package com.guokrspace.cloudschoolbus.parents;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.support.handlerui.HandlerToastUI;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.guokrspace.cloudschoolbus.parents.base.RongCloudEvent;
import com.guokrspace.cloudschoolbus.parents.base.ServerInteractions;
import com.guokrspace.cloudschoolbus.parents.base.activity.BaseActivity;
import com.android.support.fastjson.FastJsonTools;
import com.guokrspace.cloudschoolbus.parents.base.include.HandlerConstant;
import com.guokrspace.cloudschoolbus.parents.base.include.Version;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ClassEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ClassEntityDao;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ConfigEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.ConfigEntityDao;
import com.guokrspace.cloudschoolbus.parents.database.daodb.SchoolEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.SchoolEntityDao;
import com.guokrspace.cloudschoolbus.parents.database.daodb.StudentEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.StudentEntityDao;
import com.guokrspace.cloudschoolbus.parents.database.daodb.TagEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.TeacherEntity;
import com.guokrspace.cloudschoolbus.parents.database.daodb.TeacherEntityDao;
import com.guokrspace.cloudschoolbus.parents.entity.Baseinfo;

import com.guokrspace.cloudschoolbus.parents.entity.ClassInfo;
import com.guokrspace.cloudschoolbus.parents.entity.SchoolInfo;
import com.guokrspace.cloudschoolbus.parents.entity.Student;
import com.guokrspace.cloudschoolbus.parents.entity.TagT;
import com.guokrspace.cloudschoolbus.parents.entity.Teacher;
import com.guokrspace.cloudschoolbus.parents.event.BusProvider;
import com.guokrspace.cloudschoolbus.parents.event.LoginResultEvent;
import com.guokrspace.cloudschoolbus.parents.protocols.CloudSchoolBusRestClient;
import com.guokrspace.cloudschoolbus.parents.protocols.ProtocolDef;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    // UI references.
    private EditText mMobileNumberView;
    private EditText mVerifyCodeEditText;
    private Button mSigninButton;
    private View     mProgressView;
    private View     mLoginFormView;
    private Button mClicktoGetVerifyCodeButton;
    private TextView mTextViewBrand;
    private TextView mTextViewProduct;

    private String mobile;
    private String verifyCode;
    private String sid;
    private String loginToken;
    private String imToken = "IWb9/EypgQlMEo/W/o3qSLI6ZiT8q7s0UEaMPWY0lMyB3UonaGf0gmlCJbN+zU7OvAaDYa9d8U6xzmBRkFjv+Q==";
    private String userid;

//    private LoginResultEvent loginResultEvent = new LoginResultEvent();

    private Thread  thread;
    private boolean threadStopFlag = false;

    private static final int REQUEST_LIST_SIMPLE = 9;
    private static final int REQUEST_LIST_MULTIPLE = 10;
    private static final int REQUEST_LIST_SINGLE = 11;
    private static final int REQUEST_DATE_PICKER = 12;
    private static final int REQUEST_TIME_PICKER = 13;
    private static final int REQUEST_SIMPLE_DIALOG = 42;

    private int count = 0;
    private long startMillis=0;

    private Handler handler;
    {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case HandlerConstant.MSG_REG_OK:
                        showProgress(false);
                        mVerifyCodeEditText.setVisibility(View.VISIBLE);
                        mVerifyCodeEditText.setHint(getResources().getString(R.string.input_verify_code));
                        mVerifyCodeEditText.requestFocus();
                        mMobileNumberView.setEnabled(false);
                        TimerTick(60);
                        mSigninButton.setVisibility(View.VISIBLE);
                        break;
                    case HandlerConstant.MSG_REG_FAIL:
                        showProgress(false);
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.invalid_mobile));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.invalid_mobile))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        showProgress(false);
                        break;
                    //Get Session ID
                    case HandlerConstant.MSG_VERIFY_OK:
                        mApplication.mConfig = new ConfigEntity(null, sid, loginToken, mobile, userid, imToken, 0);
                        ConfigEntityDao configEntityDao = mApplication.mDaoSession.getConfigEntityDao();
                        configEntityDao.insert(mApplication.mConfig);
                        CloudSchoolBusRestClient.updateSessionid(sid);
                        httpGetTokenSuccess(imToken);
                        getBaseInfoFromServer();
                        break;

                    case HandlerConstant.MSG_LOGIN_OK_DEBUG:
                        httpGetTokenSuccess(imToken);
                        getBaseInfoFromServer();
                        break;

                    case HandlerConstant.MSG_VERIFY_FAIL:
                        showProgress(false);
                        threadStopFlag = true;
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.invalid_verify_code));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.invalid_verify_code))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        break;
                    case HandlerConstant.MSG_REG_SMS:
                        showProgress(false);
                        threadStopFlag = true;
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.SMS_oversend));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.SMS_oversend))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        break;
                    //Get the base info
                    case HandlerConstant.MSG_BASEINFO_OK:
                        BusProvider.getInstance().post(new LoginResultEvent(true));
                        finish();
                        break;
                    case HandlerConstant.MSG_BASEINFO_FAIL:
                        showProgress(false);
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.failure_baseinfo));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.failure_baseinfo))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        break;
                    case HandlerConstant.MSG_NO_NETOWRK:
                        showProgress(false);
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.no_network));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.no_network))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        break;
                    case HandlerConstant.MSG_TIMER_TICK:
                        mClicktoGetVerifyCodeButton.setText((String) msg.obj);
                        mClicktoGetVerifyCodeButton.setEnabled(false);
                        break;
                    case HandlerConstant.MSG_TIMER_TIMEOUT:
                        mClicktoGetVerifyCodeButton.setText(getResources().getString(R.string.click_get_verify_code));
                        mClicktoGetVerifyCodeButton.setEnabled(true);
                        break;
                    case HandlerConstant.LOGIN_FAILED:
                        showProgress(false);
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.failure_unknown));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.failure_unknown))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        break;
                    default:
                        showProgress(false);
                        HandlerToastUI.getHandlerToastUI(mContext, getResources().getString(R.string.failure_unknown));
//                        SimpleDialogFragment.createBuilder(mContext, getSupportFragmentManager())
//                                .setMessage(getResources().getString(R.string.failure_unknown))
//                                .setPositiveButtonText(getResources().getString(R.string.OKAY)).show();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mMobileNumberView = (EditText) findViewById(R.id.mobile);
        mVerifyCodeEditText = (EditText) findViewById(R.id.verify_code);

        mSigninButton = (Button) findViewById(R.id.sign_in_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mClicktoGetVerifyCodeButton = (Button) findViewById(R.id.sms_verifycode_button);
        mClicktoGetVerifyCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mSigninButton = (Button) findViewById(R.id.sign_in_button);
        mSigninButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attempVerify();
            }
        });

        mTextViewProduct = (TextView) findViewById(R.id.textView_product);
        mTextViewProduct.setText(Version.productNameParent);

        mTextViewBrand = (TextView) findViewById(R.id.textView_brand);
        mTextViewBrand.setText(Version.desc);
        mTextViewBrand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBrandTextClicked();
            }
        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.login_title_layout);
        View view = getSupportActionBar().getCustomView();
        TextView textView = (TextView) view.findViewById(R.id.abs_layout_titleTextView);
        textView.setText(getResources().getString(R.string.logintitle));
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptRegister() {

        // Reset errors.
        mMobileNumberView.setError(null);

        // Store values at the time of the login attempt.
        mobile = mMobileNumberView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(mobile) && !isMobileNumberValid(mobile)) {
            mMobileNumberView.setError(getString(R.string.error_incorrect_mobilenumber));
            focusView = mMobileNumberView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            register(mobile);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attempVerify() {

        // Reset errors.
        mMobileNumberView.setError(null);
        mVerifyCodeEditText.setError(null);

        // Store values at the time of the login attempt.
        mobile = mMobileNumberView.getText().toString();
        verifyCode = mVerifyCodeEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(mobile) && !isMobileNumberValid(mobile)) {
            mVerifyCodeEditText.setError(getString(R.string.error_invalid_password));
            focusView = mVerifyCodeEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            if(ServerInteractions.getInstance().isDebug && verifyCode.equals("88888888"))
            {
                //Directly call login usering super token
                if(mApplication.mConfig == null)
                {
                    mApplication.mConfig = new ConfigEntity();
                }
                mApplication.mConfig.setToken("85da0918c7d9c91b25f91ceaf795c1fb5c33bab6_123456");
                mApplication.mConfig.setMobile(mobile);
                ServerInteractions.getInstance().renew_sid(mobile,handler);
            }
            else {
                // Show a progress spinner, and kick off a background task to
                // perform the user login attempt.
                verify(verifyCode, mobile);
            }
        }
    }

    private boolean isVerifyCodeValid(String password) {
        return password.length() > 4;
    }

    private boolean isMobileNumberValid(String mobile) {
        return mobile.length() > 9;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void getBaseInfoFromServer() {

        if(!mApplication.networkStatusEvent.isNetworkConnected()) {
            handler.sendEmptyMessage(HandlerConstant.MSG_NO_NETOWRK);
            return;
        }

        showProgress(true);

        RequestParams params = new RequestParams();

        CloudSchoolBusRestClient.get(ProtocolDef.METHOD_baseinfo, params, new JsonHttpResponseHandler() {

                    SchoolEntityDao schoolEntityDao = mApplication.mDaoSession.getSchoolEntityDao();
                    ClassEntityDao classEntityDao = mApplication.mDaoSession.getClassEntityDao();
                    TeacherEntityDao teacherEntityDao = mApplication.mDaoSession.getTeacherEntityDao();
                    StudentEntityDao studentEntityDao = mApplication.mDaoSession.getStudentEntityDao();

                    public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                        String retCode = "";

                        for (int i = 0; i < headers.length; i++) {
                            Header header = headers[i];
                            if ("code".equalsIgnoreCase(header.getName())) {
                                retCode = header.getValue();
                                break;
                            }
                        }

                        if (!retCode.equals("1")) {
                            handler.sendEmptyMessage(HandlerConstant.MSG_BASEINFO_FAIL);
                            return;
                        }

                        Object baseinfo;
                        baseinfo = FastJsonTools.getObject(response.toString(), Baseinfo.class);

                        //Save School Information
                        if(baseinfo instanceof Baseinfo ) {

                            for (SchoolInfo schoolInfo : ((Baseinfo)baseinfo).getSchools()) {
                                SchoolEntity schoolEntity = new SchoolEntity();
                                schoolEntity.setId(schoolInfo.getSchoolid());
                                schoolEntity.setAddress(schoolInfo.getAddress());
                                schoolEntity.setLogo(schoolInfo.getLogo());
                                schoolEntity.setCover(schoolInfo.getCover());
                                schoolEntity.setName(schoolInfo.getSchoolname());

                                schoolEntityDao.insertOrReplace(schoolEntity);

                                //Save the TagEntities
                                for(TagT tag:schoolInfo.getTags())
                                {
                                    TagEntity tagDb = new TagEntity();
                                    tagDb.setTagid(tag.getTagid());
                                    tagDb.setTagName(tag.getTagname());
                                    tagDb.setTagname_en(tag.getTagname_en());
                                    tagDb.setTagnamedesc(tag.getTagnamedesc());
                                    tagDb.setTagnamedesc_en(tag.getTagnamedesc_en());
                                    mApplication.mDaoSession.getTagEntityDao().insertOrReplace(tagDb);
                                }

                                //Save Class Information
                                List<ClassInfo> classes =schoolInfo.getClasses();
                                for (int j = 0; j < classes.size(); j++) {
                                    ClassInfo classinfo = classes.get(j);
                                    ClassEntity classEntity = new ClassEntity(classes.get(j).getClassid(), classes.get(j).getClassname(), schoolEntity.getId());
                                    classEntityDao.insertOrReplace(classEntity);

                                    //Save teacher information
                                    List<Teacher> teachers = classes.get(j).getTeacher();
                                    for (int k = 0; k < teachers.size(); k++) {
                                        TeacherEntity teacherEntity = new TeacherEntity(teachers.get(k).getId(), teachers.get(k).getDuty(),
                                                teachers.get(k).getAvatar(), teachers.get(k).getName() ,classEntity.getClassid());
                                        teacherEntityDao.insertOrReplace(teacherEntity);
                                    }

                                    //Students(Student and class are many2many relationship)
                                    List<Student> students = ((Baseinfo)baseinfo).getStudents(); //Student list from server
                                    List<String> studentInClass = classinfo.getStudent(); //Studentid in class
                                    // if found a student in class, instantiate the student entity for the DB
                                    for (int m = 0; m < studentInClass.size(); m++) {
                                        String studentid = studentInClass.get(m);
                                        for (int n = 0; n < students.size(); n++) {
                                            if (studentid.equals(students.get(n).getStudentid())) {
                                                StudentEntity studentEntity = new StudentEntity(students.get(n).getCnname(), students.get(n).getBirthday(), students.get(n).getSex(), students.get(n).getAvatar(), students.get(n).getNickname(), studentid, classEntity.getClassid());
                                                studentEntityDao.insertOrReplace(studentEntity);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        mApplication.initBaseinfo();

                        handler.sendEmptyMessage(HandlerConstant.MSG_BASEINFO_OK);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        handler.sendEmptyMessage(HandlerConstant.MSG_BASEINFO_FAIL);
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        handler.sendEmptyMessage(HandlerConstant.MSG_BASEINFO_FAIL);
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        handler.sendEmptyMessage(HandlerConstant.MSG_BASEINFO_FAIL);
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                }
        );
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public void register(String phonenum) {

        if(!mApplication.networkStatusEvent.isNetworkConnected()) {
            handler.sendEmptyMessage(HandlerConstant.MSG_NO_NETOWRK);
            return;
        }

        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("mobile", phonenum);
        CloudSchoolBusRestClient.get(ProtocolDef.METHOD_register, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {

                String retCode = "";

                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    if ("code".equalsIgnoreCase(header.getName())) {
                        retCode = header.getValue();
                        break;
                    }
                }

                if (!retCode.equals("1")) {
                    handler.sendEmptyMessage(HandlerConstant.MSG_REG_FAIL);
                    return;
                } else
                    handler.sendEmptyMessage(HandlerConstant.MSG_REG_OK);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String retCode = "";

                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    if ("code".equalsIgnoreCase(header.getName())) {
                        retCode = header.getValue();
                        break;
                    }
                }

                if(retCode.equals("-1117"))
                {
                    handler.sendEmptyMessage(HandlerConstant.MSG_REG_FAIL);
                } else if(retCode.equals("-1120")) {
                    handler.sendEmptyMessage(HandlerConstant.MSG_REG_SMS);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                handler.sendEmptyMessage(HandlerConstant.MSG_REG_FAIL);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.sendEmptyMessage(HandlerConstant.MSG_REG_FAIL);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONArray errorResponse) {
                handler.sendEmptyMessage(HandlerConstant.MSG_REG_FAIL);
                System.out.println(errorResponse);
            }
        });
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public void verify(String verify_code, String mobile) {

        if(!mApplication.networkStatusEvent.isNetworkConnected()) {
            handler.sendEmptyMessage(HandlerConstant.MSG_NO_NETOWRK);
            return;
        }

        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("verifycode", verify_code);
        params.put("mobile",mobile);

        CloudSchoolBusRestClient.post("verify", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String retCode = "";

                for (int i = 0; i < headers.length; i++) {
                    Header header = headers[i];
                    if ("code".equalsIgnoreCase(header.getName())) {
                        retCode = header.getValue();
                        break;
                    }
                }

                if (retCode.equals("1")) {
                    try {
                        sid = response.getString("sid");
                        loginToken = response.getString("token");
                        userid = response.getString("userid");
                        if (response.getString("rongtoken") != null || !response.getString("rongtoken").isEmpty())
                            imToken = response.getString("rongtoken");
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }

                    handler.sendEmptyMessage(HandlerConstant.MSG_VERIFY_OK);
                } else {
                    handler.sendEmptyMessage(HandlerConstant.MSG_VERIFY_FAIL);
                }

                return;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                handler.sendEmptyMessage(HandlerConstant.MSG_VERIFY_FAIL);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                handler.sendEmptyMessage(HandlerConstant.LOGIN_FAILED);
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, org.json.JSONArray errorResponse) {
                handler.sendEmptyMessage(HandlerConstant.LOGIN_FAILED);
                System.out.println(errorResponse);
            }
        });
    }

    private void TimerTick(final int max_seconds) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int seconds_left = max_seconds;
                while (seconds_left > 0 && !threadStopFlag) {
                    seconds_left--;
                    handler.sendMessage(handler.obtainMessage(HandlerConstant.MSG_TIMER_TICK, seconds_left + getResources().getString(R.string.seconds)));
                    try {
                        thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(HandlerConstant.MSG_TIMER_TIMEOUT);
            }
        });
        if (!thread.isAlive()) {
            thread.start();
        }
    }

    private void httpGetTokenSuccess(String token) {

    /* IMKit SDK调用第二步 建立与服务器的连接 */

    /* 集成和测试阶段，您可以直接使用从您开发者后台获取到的 token，比如 String token = “d6bCQsXiupB......”; */
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String userId) {
                InputProvider.ExtendProvider[] provider = {
                        new ImageInputProvider(RongContext.getInstance()),//图片
                        new CameraInputProvider(RongContext.getInstance()),//相机
//                        new LocationInputProvider(RongContext.getInstance()),//地理位置
//                        new VoIPInputProvider(RongContext.getInstance()),// 语音通话
                };
                RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);
                RongCloudEvent.getInstance().setOtherListener();
            /* 连接成功 */
            }

            @Override
            public void onError(RongIMClient.ErrorCode e) {
            /* 连接失败，注意并不需要您做重连 */
            }

            @Override
            public void onTokenIncorrect() {
            /* Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token */
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    public boolean onBrandTextClicked() {

        //get system current milliseconds
        long time= System.currentTimeMillis();

        //if it is the first time, or if it has been more than 3 seconds since the first tap ( so it is like a new try), we reset everything
        if (startMillis==0 || (time-startMillis> 3000) ) {
            startMillis=time;
            count=1;
        }
        //it is not the first, and it has been  less than 3 seconds since the first
        else{ //  time-startMillis< 3000
            count++;
        }

        if (count==5) {
            ServerInteractions.getInstance().isDebug = true;
            HandlerToastUI.getHandlerToastUI(mContext,"Debug Mode is on");
        }

        return ServerInteractions.getInstance().isDebug;
    }

}

