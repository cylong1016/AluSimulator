import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/*
 * 1.首先你只需要看旁边控制台的输出就好了
 * 2.如果左边出现了Errors，这是你的程序在运行中出现了错误，而不是计算结果错误
 * 3.由于浮点数的精度问题，我不知道怎么样使自己的输出和计算机算出来的一样，所以用了字符串的相似度来判断结果
 * 4.我不知道老师的测试是什么样的，可以先测试一下我的代码，结果近似和计算机的一样
 * 5.这个测试代码没有考虑非数，无穷，和反规格化，都是正常的数，范围是-4000到4000
 * 6.也无法检测你的舍入方式和进位那些边边角角什么的，反正就是能测个大概【原谅我比较懒……】
 * 7.有bug或者不足请告诉我啦
 * 8.其实这个自己完全可以在main里写一个for循环实现，我没时间写测试那些边边角角的代码了→_→
 */
/**
 * 
 * @author cylong
 * @version Jun 9, 2014 9:03:33 AM
 */
@RunWith(Parameterized.class)
public class ALUFloatTest {

	/** 需要测试的类 */
	private ALU alu = new ALU();
	private String number1;
	private String number2;
	private ALU.Operation operation;
	private String TrueResult;

	@Parameters
	public static List<Object[]> data () {
		ArrayList<Object[]> objArr = new ArrayList<Object[]>();
		Object[] temp;
		float num1;
		float num2;

		int range = 4000;
		int range2 = range << 1;

		for (int i = 0; i < 1000; i++) {
			temp = new Object[4];

			num1 = (float) (range2 * Math.random() - range);
			num2 = (float) (range2 * Math.random() - range);

			switch ((int) (4 * Math.random())) {
			case 0:
				temp[2] = ALU.Operation.ADDITION;
				temp[3] = String.format("%.10f", num1 + num2);
				break;
			case 1:
				temp[2] = ALU.Operation.SUBTRACTION;
				temp[3] = String.format("%.10f", num1 - num2);
				break;
			case 2:
				temp[2] = ALU.Operation.MULTIPLICATION;
				temp[3] = String.format("%.10f", num1 * num2);

				break;
			case 3:
				temp[2] = ALU.Operation.DIVISION;
				// 除数和被除数
				while (num2 == 0 || (num1 % num2) == 0) {
					num1 = (float) (range2 * Math.random() - range);
					num2 = (float) (range2 * Math.random() - range);
				}
				temp[3] = String.format("%.10f", num1 / num2);
				break;
			}
			temp[0] = String.valueOf(num1);
			temp[1] = String.valueOf(num2);

			objArr.add(temp);
		}

		Object[][] res = new Object[objArr.size()][];
		objArr.toArray(res);

		return Arrays.asList(res);
	}

	/**
	 * @param number1
	 *            操作数 1，用十进制表示，为被加数/被减数/被乘数/被除数
	 * @param number2
	 *            操作数 2，用十进制表示，为加数/减数/乘数/除数
	 * @param operation
	 *            操作类型
	 * @param result
	 *            计算结果，用十进制表示。其中，加法返回和，减法返回差，乘法返回积，除法返回商。如果是负数，最左边为“-”；如果是正数或
	 *            0，不 需要符号位
	 * @author cylong
	 * @version Jun 9, 2014 9:14:13 AM
	 */
	public ALUFloatTest (String number1, String number2, ALU.Operation operation, String result) {
		super();
		this.number1 = number1;
		this.number2 = number2;
		this.operation = operation;
		this.TrueResult = result;
	}

	/** 相似度转百分比 */
	public static String similarityResult (double resule) {
		return NumberFormat.getPercentInstance(new Locale("en ", "US ")).format(resule);
	}

	/**
	 * 相似度比较
	 * 
	 * @param strA
	 * @param strB
	 * @return
	 */
	public static double SimilarDegree (String strA, String strB) {
		String newStrA = removeSign(strA);
		String newStrB = removeSign(strB);
		int temp = Math.max(newStrA.length(), newStrB.length());
		int temp2 = longestCommonSubstring(newStrA, newStrB).length();
		return temp2 * 1.0 / temp;
	}

	private static String removeSign (String str) {
		StringBuffer sb = new StringBuffer();
		for (char item : str.toCharArray())
			if (charReg(item)) {
				sb.append(item);
			}

		return sb.toString();
	}

	private static boolean charReg (char charValue) {
		return (charValue >= 0x4E00 && charValue <= 0X9FA5) || (charValue >= 'a' && charValue <= 'z') || (charValue >= 'A' && charValue <= 'Z') || (charValue >= '0' && charValue <= '9');
	}

	private static String longestCommonSubstring (String strA, String strB) {
		char[] chars_strA = strA.toCharArray();
		char[] chars_strB = strB.toCharArray();
		int m = chars_strA.length;
		int n = chars_strB.length;
		int[][] matrix = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (chars_strA[i - 1] == chars_strB[j - 1])
					matrix[i][j] = matrix[i - 1][j - 1] + 1;
				else
					matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);
			}
		}

		char[] result = new char[matrix[m][n]];
		int currentIndex = result.length - 1;
		while (matrix[m][n] != 0) {
			if (matrix[n] == matrix[n - 1])
				n--;
			else if (matrix[m][n] == matrix[m - 1][n])
				m--;
			else {
				result[currentIndex] = chars_strA[m - 1];
				currentIndex--;
				n--;
				m--;
			}
		}
		return new String(result);
	}

	public String extendsString (String str, int length) {
		StringBuilder result = new StringBuilder(str);
		for (int i = str.length(); i < length; i++) {
			result.append("0");
		}
		return result.toString();
	}

	/**
	 * Test method for
	 * {@link ALU#Calculation(java.lang.String, java.lang.String, ALU.Type, ALU.Operation, int[])}
	 */
	@Test(timeout = 1000)
	public void testCalculation () {
		String YourResult = alu.Calculation(number1, number2, ALU.Type.FLOAT, operation, new int[] { 23, 8, 4 });
		if (YourResult.length() < TrueResult.length()) {
			YourResult = extendsString(YourResult, TrueResult.length());
		} else if (YourResult.length() > TrueResult.length()) {
			TrueResult = extendsString(TrueResult, YourResult.length());
		}
		double result = SimilarDegree(YourResult, TrueResult);
		if (result >= 0.6) {
			System.out.println("相似度很高！" + similarityResult(result));
		} else {
			System.out.println();
			System.out.println("相似度不高！" + similarityResult(result));
			System.out.println("operation:" + operation);
			System.out.println("number1:" + number1);
			System.out.println("number2:" + number2);
			System.out.println("YourResult:" + YourResult);
			System.out.println("TrueResult:" + TrueResult);
			System.out.println();
		}
	}

}
