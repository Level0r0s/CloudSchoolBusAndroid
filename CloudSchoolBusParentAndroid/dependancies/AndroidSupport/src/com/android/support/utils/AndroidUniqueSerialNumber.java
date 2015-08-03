package com.android.support.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * android唯一序列号
 * 
 * @author lenovo
 * 
 */
public class AndroidUniqueSerialNumber {
	
	private static Object sObj = new Object();

	private static String sUniqueSerialNumber = null;

	private static AndroidUniqueSerialNumber sAndroidUniqueSerialNumber;

	public static AndroidUniqueSerialNumber getInstance(Context context) {

		if (null == sAndroidUniqueSerialNumber) {
			synchronized (sObj) {
				if (null == sAndroidUniqueSerialNumber) {
					sAndroidUniqueSerialNumber = new AndroidUniqueSerialNumber(
							context);
				}
			}
		}

		return sAndroidUniqueSerialNumber;
	}

	public AndroidUniqueSerialNumber(Context context) {

		uniqueSerialNumber(context);
	}

	private void uniqueSerialNumber(Context context) {
		// 如何获取Android唯一标识（唯一序列号）
		// 作者：kim365更新于 05月06日访问（3944）评论（4）
		// 有很多场景和需求你需要用到手机设备的唯一标识符。
		// 在Android中，有以下几种方法获取这样的ID。
		// 1. The IMEI: 仅仅只对Android手机有效:
		TelephonyManager TelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String m_szImei = null;
		if (null != TelephonyMgr) {
			m_szImei = TelephonyMgr.getDeviceId();
		}
		// 采用此种方法，需要在AndroidManifest.xml中加入一个许可：android.permission.READ_PHONE_STATE，并且用户应当允许安装此应用。作为手机来讲，IMEI是唯一的，它应该类似于
		// 359881030314356（除非你有一个没有量产的手机（水货）它可能有无效的IMEI，如：0000000000000）。

		// 2. Pseudo-Unique ID, 这个在任何Android手机中都有效
		// 有一些特殊的情况，一些如平板电脑的设置没有通话功能，或者你不愿加入READ_PHONE_STATE许可。而你仍然想获得唯一序列号之类的东西。这时你可以通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点。这样计算出来的ID不是唯一的（因为如果两个手机应用了同样的硬件以及Rom
		// 镜像）。但应当明白的是，出现类似情况的可能性基本可以忽略。要实现这一点，你可以使用Build类:

		String m_szDevIDShort = "35"
				+ // we make this look like a valid IMEI
				Build.BOARD.length() % 10 + Build.BRAND.length() % 10
				+ Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
				+ Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
				+ Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
				+ Build.TAGS.length() % 10 + Build.TYPE.length() % 10
				+ Build.USER.length() % 10; // 13 digits
		// 大多数的Build成员都是字符串形式的，我们只取他们的长度信息。我们取到13个数字，并在前面加上“35”。这样这个ID看起来就和15位IMEI一样了。

		// 3. The Android ID
		// 通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，如果某个Andorid手机被Root过的话，这个ID也可以被任意改变。

		String m_szAndroidID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		// Returns: 9774d56d682e549c . 无需任何许可。

		// 4. The WLAN MAC Address string
		// 是另一个唯一ID。但是你需要为你的工程加入android.permission.ACCESS_WIFI_STATE
		// 权限，否则这个地址会为null。

		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		String m_szWLANMAC = null;
		if (null != wm && null != wm.getConnectionInfo()) {
			m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
		}
		// Returns: 00:11:22:33:44:55
		// (这不是一个真实的地址。而且这个地址能轻易地被伪造。).WLan不必打开，就可读取些值。

		// 5. The BT MAC Address string
		// 只在有蓝牙的设备上运行。并且要加入android.permission.BLUETOOTH 权限.

		BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
		m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		String m_szBTMAC = null;
		if (null != m_BluetoothAdapter) {
			m_szBTMAC = m_BluetoothAdapter.getAddress();
		}
		// Returns: 43:25:78:50:93:38 . 蓝牙没有必要打开，也能读取。

		// Combined Device ID
		// 综上所述，我们一共有五种方式取得设备的唯一标识。它们中的一些可能会返回null，或者由于硬件缺失、权限问题等获取失败。
		// 但你总能获得至少一个能用。所以，最好的方法就是通过拼接，或者拼接后的计算出的MD5值来产生一个结果。

		String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID
				+ m_szWLANMAC + m_szBTMAC;
		// compute md5
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
		// get md5 bytes
		byte p_md5Data[] = m.digest();
		// create a hex string
		String m_szUniqueID = new String();
		for (int i = 0; i < p_md5Data.length; i++) {
			int b = (0xFF & p_md5Data[i]);
			// if it is a single digit, make sure it have 0 in front (proper
			// padding)
			if (b <= 0xF)
				m_szUniqueID += "0";
			// add number to string
			m_szUniqueID += Integer.toHexString(b);
		} // hex string to uppercase
		m_szUniqueID = m_szUniqueID.toUpperCase();
		// 通过以上算法，可产生32位的16进制数据:
		// 9DDDF85AFF0A87974CE4541BD94D5F55
		// 现在你就可以对其进行你的应用了。
		sUniqueSerialNumber = m_szUniqueID;
	}

	public String getAndroidUniqueSerialNumber() {
		return sUniqueSerialNumber;
	}
}