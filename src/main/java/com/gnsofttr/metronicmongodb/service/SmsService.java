package com.gnsofttr.metronicmongodb.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

	public String smsSender(String phoneNumber) throws Exception {

		// kullanıcı adı
		String username = "kullanıcı adı";
		// kullanıcı şifresi
		String password = "şifre";
		// mesaj başlığı
		String header = "kodu gönderecek telefon numarası";

		Random rand = new Random();
		int Low = 100000;
		int High = 999999;
		int i = rand.nextInt(High - Low) + Low;
		String code = Integer.toString(i);

		String msg = URLEncoder.encode("Doğrulama Kodu:" + i, StandardCharsets.UTF_8.toString());

		String url = "https://api.netgsm.com.tr/sms/send/otp/?usercode=" + username + "&password=" + password + "&no="
				+ phoneNumber + "&msg=" + msg + "&msgheader=" + header + "";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("rapor durum = " + response.toString());

		return code;
	}
}
