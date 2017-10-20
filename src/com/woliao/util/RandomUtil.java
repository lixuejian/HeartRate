package com.woliao.util;

import java.util.Random;

public class RandomUtil {
	public static String getRandomUsername(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < 6; i++){
			//目的:产生随机字母
			Random random = new Random();
			//产生[97-122]之间的随机数
			int randomNum = random.nextInt(26)+97;
			//强转成字符型
			char c = (char)randomNum;
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String getRandomPassword(){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < 6; i++){
			//目的:产生0到9之间的int类型随机数，不包括9，也就是[0,8]
			Random random = new Random();
			int randomNum = random.nextInt(9) + 1;
			sb.append(randomNum);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(RandomUtil.getRandomPassword());
	}
}
