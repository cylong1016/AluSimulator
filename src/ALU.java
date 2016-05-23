/*-----------------“计算机组织结构 ”编程练习 03-------------------*/
/*-----------------请允许我做一个悲伤的表情【→_→】-----------------*/
/**
 * <h6>模拟 ALU 进行整数、浮点数和十进制数的四则运算</h6><br>
 * <h6>注意：</h6>
 * <ol>
 * <li>在AluSimulator.rar所包含工程的基础上，采用Java编程。</li>
 * <li>只能修改ALU.java文件内容，可以新建方法，但不得新建其它文件。在 ALU.java的开头处添加注释，标明学号和姓名。</li>
 * <li>所有方法的实现必须采用指定算法，其过程必须与课件上一致。</li>
 * <li>如果提交结果不能正常被测试，可以有一次解释机会，但不得修改。最终无法被测试的提交结果计0分。</li>
 * </ol>
 * @author 陈云龙 131250181
 * @version May 23, 2014 23:59:59 PM
 * @version Jun 10, 2014 23:59:59 PM
 * @version Jun 20, 2014 23:59:59 PM
 */
public class ALU {

	/*---------------------------------------------主函数---------------------------------------------*/
	public static void main(String[] args) {
		// ALU alu = new ALU();
	}

	/**
	 * @author cylong
	 * @version Jun 13, 2014 2:43:58 PM
	 */
	public class 信息 {

		private final String 姓名 = "陈云龙";
		private final String 学号 = "131250181";

		public String get姓名() {
			return 姓名;
		}

		public String get学号() {
			return 学号;
		}

	}

	/**
	 * <h6>操作类型：Operation</h6>
	 * <p>
	 * 可以取的值包括：ADDITION、SUBTRACTION、MULTIPLICATION、DIVISION
	 * </p>
	 * @author cylong
	 * @version May 21, 2014 4:58:40 PM
	 */
	public enum Operation {
		ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
	};

	/**
	 * <h6>操作数类型：Type</h6>
	 * <p>
	 * 可以取的值包括：INTEGER、FLOAT、DECIMAL<br>
	 * 十进制整数不需进行转换为二进制，直接进行运算。
	 * </p>
	 * @author cylong
	 * @version May 21, 2014 5:02:04 PM
	 */
	public enum Type {
		INTEGER, FLOAT, DECIMAL
	};

	/**
	 * 该方法用于模拟左移操作，通过对字符串操作实现
	 * @param operand
	 *            操作数，用补码表示
	 * @param n
	 *            左移的位数
	 * @return 左移的结果
	 * @author cylong
	 * @version May 22, 2014 12:15:50 AM
	 */
	public String LeftShift(String operand, int n) {
		// 移动位数超过操作数长度
		if (n >= operand.length()) { return extendBit("0", operand.length()); }
		StringBuilder result = new StringBuilder(operand.substring(n));
		for(int i = 0; i < n; i++) {
			result.append('0');
		}
		return result.toString();
	}

	/**
	 * 该方法用于逻辑右移操作数
	 * @param operand
	 *            操作数
	 * @param n
	 *            右移位数
	 * @return 右移后的结果
	 * @author cylong
	 * @version Jun 8, 2014 12:09:06 PM
	 */
	public String logicalRightShift(String operand, int n) {
		// 移动位数超过操作数长度
		if (n >= operand.length()) { return extendBit("0", operand.length()); }
		StringBuilder result = new StringBuilder(operand.substring(0, operand.length() - n));
		for(int i = 0; i < n; i++) {
			result.insert(0, "0");
		}
		return result.toString();
	}

	/**
	 * 该方法用于模拟右移操作，通过对字符串操作实现
	 * @param operand
	 *            操作数，用补码表示
	 * @param n
	 *            右移的位数
	 * @return 右移的结果，高位补符号位
	 * @author cylong
	 * @version May 22, 2014 1:29:17 AM
	 */
	public String RightShift(String operand, int n) {
		// 移动位数超过操作数长度
		if (n >= operand.length()) { return extendBit(operand.substring(0, 1), operand.length()); }
		StringBuilder result = new StringBuilder(operand.substring(0, operand.length() - n));
		for(int i = 0; i < n; i++) {
			result.insert(0, result.charAt(0));
		}
		return result.toString();
	}

	/**
	 * 对二进制操作数位数进行扩展，在高位补符号位
	 * @param operand
	 *            操作数
	 * @param length
	 *            扩展后长度
	 * @return 扩展后的二进制字符串
	 * @author cylong
	 * @version May 22, 2014 6:04:56 AM
	 */
	private String extendBit(String operand, int length) {
		StringBuilder result = new StringBuilder(operand);
		for(int i = result.length(); i < length; i++) {
			result.insert(0, result.charAt(0));
		}
		return result.toString();
	}

	/**
	 * 该方法用于生成十进制整数的补码表示<br>
	 * 采用每位与1与得到该位的算法
	 * @param number
	 *            十进制整数的字符串表示,如果是负数,最左边为“-”；如果是正数或 0，不 需要符号位
	 * @param length
	 *            表示成二进制的长度
	 * @return 十进制的补码表示，长度为 length
	 * @author cylong
	 * @version May 21, 2014 5:11:57 PM
	 */
	public String Complement(String number, int length) {
		int value = Integer.parseInt(number);
		StringBuilder builder = new StringBuilder(length);
		for(int i = 0; i < length; i++) {
			builder.insert(0, value & 1);
			value = value >> 1;
		}
		return builder.toString();
	}

	/**
	 * 该方法用于生成十进制浮点数的二进制表示
	 * @param number
	 *            十进制浮点数，如果是负数，最左边为“-”；如果是正数或 0， 不需要符号位
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @return number 的二进制表示，长度为 1+sLength+eLength。从左向右依
	 *         次为符号、指数（移码表示）、尾数（首位隐藏），需要考虑 0、反规格化 表示、无穷、NaN 等因素，具体借鉴 IEEE 754
	 * @author cylong
	 * @version Jun 2, 2014 3:04:23 AM
	 */
	public String FloatRepresentation(String number, int sLength, int eLength) {
		double value = Double.parseDouble(number);
		// 　是否为负数
		boolean negative = false;
		value = (negative = value < 0) ? -value : value;

		String preValue = String.valueOf((int)value);
		String sufValue = String.valueOf(value - (int)value);
		if (value == 0) {
			// 0的话直接返回
			return extendBit("0", 1 + sLength + eLength);
		}
		int exp = 0;
		int index = 0;
		String binPreValue = Complement(preValue, 64);
		String significand = fractionComplement(sufValue, sLength);
		while(index < binPreValue.length() && binPreValue.charAt(index) != '1') {
			index++;
		}
		binPreValue = binPreValue.substring(index);
		exp += binPreValue.length() - 1;
		exp = exp < 0 ? 0 : exp;
		if (exp == 0) {
			index = 0;
			while(index < significand.length() && significand.charAt(index) != '1') {
				index++;
			}
			exp -= index + 1;
			significand = LeftShift(significand, index + 1);
		} else {
			significand = (binPreValue.substring(1) + significand).substring(0, sLength);
		}
		String exponent = Complement(Integer.toString((int)(exp + Math.pow(2, eLength - 1) - 1)), eLength);
		return (negative ? 1 : 0) + exponent + significand;
	}

	/**
	 * 用于生成小数部分的补码形式
	 * @param string
	 *            浮点数的字符串形式【十进制】
	 * @param sLength
	 *            字符串长度
	 * @return 小数部分的二进制形式
	 * @author cylong
	 * @version Jun 2, 2014 4:23:57 AM
	 */
	private String fractionComplement(String string, int sLength) {
		StringBuilder bin = new StringBuilder();
		double value = Double.parseDouble("0." + string.split("\\.")[1]);
		while(true) {
			value *= 2;
			if (value == 0) {
				bin.append('0');
				break;
			} else if (value > 1.0) {
				value -= 1.0;
				bin.append('1');
			} else if (value < 1.0) {
				bin.append('0');
			} else if (value == 1.0) {
				bin.append('1');
				break;
			}
			// 超出位数则跳出
			if (bin.length() >= sLength + 1) {
				break;
			}
		}
		// 不足则补充位数
		for(int i = bin.length(); i < sLength + 1; i++) {
			bin.append('0');
		}
		// 舍入处理
		if (bin.charAt(bin.length() - 1) == '0') {
			return bin.substring(0, bin.length() - 1).toString();
		} else {
			return Addition(bin.substring(0, bin.length() - 1).toString(), "01", '0', sLength).substring(0, sLength);
		}
	}

	/**
	 * 该方法用于生成十进制浮点数的 IEEE 754 表示，要求调用 Float 实现
	 * @param number
	 *            十进制浮点数。如果是负数，最左边为“-”；如果是正数或 0， 不需要符号位
	 * @param length
	 *            二进制表示的长度，为 32 或 64
	 * @return number 的二进制表示，长度为 length，从左向右依次为符号、指数（移码表示）、尾数（首位隐藏）
	 * @author cylong
	 * @version Jun 2, 2014 4:08:45 PM
	 */
	public String IEEE754(String number, int length) {
		switch(length) {
		case 32:
			return FloatRepresentation(number, 23, 8);
		case 64:
			return FloatRepresentation(number, 52, 11);
		default:
			return null;
		}
	}

	/**
	 * 该方法用于生成十进制整数的 NBCD 表示
	 * @param number
	 *            十进制整数。如果是负数，最左边为“-”；如果是正数或 0，不需要符号位
	 * @return number 的 NBCD 表示。“+”表示为 1100，“-”表示为 1101
	 * @author cylong
	 * @version Jun 13, 2014 2:38:02 PM
	 */
	public String NBCD(String number) {
		StringBuilder result = new StringBuilder();
		boolean negative;
		if (negative = number.charAt(0) == '-') {
			number = number.substring(1);
		}
		for(char ch : number.toCharArray()) {
			// 这样做的原因是比较快→_→
			// 但是代码比较长→_→
			switch(ch) {
			case '0':
				result.append("0000");
				break;
			case '1':
				result.append("0001");
				break;
			case '2':
				result.append("0010");
				break;
			case '3':
				result.append("0011");
				break;
			case '4':
				result.append("0100");
				break;
			case '5':
				result.append("0101");
				break;
			case '6':
				result.append("0110");
				break;
			case '7':
				result.append("0111");
				break;
			case '8':
				result.append("1000");
				break;
			case '9':
				result.append("1001");
				break;
			}
		}
		return (negative ? "1101" : "1100") + result.toString();
	}

	/**
	 * 该方法用于计算补码表示的操作数的真值
	 * @param operand
	 *            操作数
	 *            <ul>
	 *            <li>当 type 为 INTEGER 时，用补码形式的二进制表示</li>
	 *            <li>当 type 为 FLOAT 时，用原码形式的二进制表示</li>
	 *            <li>当 type 为 DECIMAL 时，用 NBCD 表示</li>
	 *            </ul>
	 * @param type
	 *            操作数类型
	 * @param length
	 *            <ul>
	 *            <li>当 type 为 INTEGER 时，忽略该参数</li>
	 *            <li>当 type 为 FLOAT 时，包含 2 个元素，依次为尾数的长度和指数的长 度</li>
	 *            <li>当 type 为 DECIMAL 时，忽略该参数</li>
	 *            <li></li>
	 *            </ul>
	 * @return 该操作数的真值，如果是负数，最左边为“-”；如果是正数或 0，不 需要符号位
	 * @author cylong
	 * @version Jun 2, 2014 4:37:55 PM
	 * @version Jun 13, 2014 3:13:38 PM
	 */
	public String TrueValue(String operand, Type type, int[] length) {
		switch(type) {
		case INTEGER:
			return integerTrueValue(operand);
		case FLOAT:
			return floatTrueValue(operand, length);
		case DECIMAL:
			return decimalTrueValue(operand);
		default:
			return null;
		}
	}

	/**
	 * @param operand
	 *            用 NBCD 表示
	 * @return DECIMAL 的真值
	 * @author cylong
	 * @version Jun 13, 2014 3:16:52 PM
	 */
	private String decimalTrueValue(String operand) {
		StringBuilder result = new StringBuilder(operand.charAt(3) == '1' ? "-" : "");
		for(int i = 4; i < operand.length(); i += 4) {
			// 这样做的目的是比较快→_→
			char bitResult = '0';
			bitResult += operand.charAt(i + 0) == '1' ? 8 : 0;
			bitResult += operand.charAt(i + 1) == '1' ? 4 : 0;
			bitResult += operand.charAt(i + 2) == '1' ? 2 : 0;
			bitResult += operand.charAt(i + 3) == '1' ? 1 : 0;
			result.append(bitResult);
		}
		return result.toString();
	}

	/**
	 * @param operand
	 *            操作数（补码表示）
	 * @return 操作数的真值【整数】
	 * @author cylong
	 * @version May 21, 2014 6:54:58 PM
	 */
	private String integerTrueValue(String operand) {
		char[] charArr = operand.toCharArray();
		int trueValue = -(charArr[0] - '0'); // 操作数的真值
		for(int i = 1; i < charArr.length; i++) {
			trueValue = (trueValue << 1) + charArr[i] - '0';
		}
		return Integer.toString(trueValue);
	}

	/**
	 * @param operand
	 *            操作数（补码表示）
	 * @param length
	 *            包含 2 个元素，依次为尾数的长度和指数的长 度
	 * @return 操作数的真值【浮点数】
	 * @author cylong
	 * @version Jun 2, 2014 5:00:24 PM
	 */
	private String floatTrueValue(String operand, int[] length) {
		String oneExponent = extendBit("1", length[1]);
		String zeroExponent = extendBit("0", length[1]);
		String zeroSignificand = extendBit("0", length[0]);
		String significand = operand.substring(length[1] + 1);
		String exponent = operand.substring(1, length[1] + 1);
		if (exponent.equals(zeroExponent) && significand.equals(zeroSignificand)) {
			return "0";
		} else if (exponent.equals(oneExponent) && significand.equals(zeroSignificand)) {
			switch(operand.charAt(0)) {
			case '0':
				return "+Inf";
			case '1':
				return "-Inf";
			default:
				return null;
			}
		} else if (exponent.equals(oneExponent) && !significand.equals(zeroSignificand)) {
			return "NaN";
		} else if (exponent.equals(zeroExponent) && !significand.equals(zeroSignificand)) {
			return denormalized(significand, length[0], length[1], operand.charAt(0));
		} else {
			return normalizedNonzero(operand.charAt(0), exponent, significand, length[0], length[1]);
		}
	}

	/**
	 * 此方法用于计算规格化非零数
	 * @param sign
	 *            符号
	 * @param exponent
	 *            指数【二进制】
	 * @param significand
	 *            有效数【二进制】
	 * @param sLength
	 *            有效数的长度
	 * @param eLength
	 *            指数的长度
	 * @return 该操作数的真值，如果是负数，最左边为“-”；如果是正数或 0，不 需要符号位
	 * @author cylong
	 * @version Jun 2, 2014 7:54:27 PM
	 */
	private String normalizedNonzero(char sign, String exponent, String significand, int sLength, int eLength) {
		int intExponentBias = (int)(Math.pow(2, eLength - 1) - 1);
		int intExponent = Integer.parseInt(integerTrueValue("0" + exponent)) - intExponentBias;
		double dSignificand = Double.parseDouble(fractionTrueValue(significand, sLength)) + 1.0;
		return (sign == '0' ? "" : "-") + Double.toString(Math.pow(2, intExponent) * dSignificand);
	}

	/**
	 * 计算浮点数的反规格化数
	 * @param significand
	 *            有效数【二进制】
	 * @param sLength
	 *            有效数长度
	 * @param eLength
	 *            指数的长度
	 * @param sign
	 *            符号
	 * @return 该操作数的真值，如果是负数，最左边为“-”；如果是正数或 0，不 需要符号位
	 * @author cylong
	 * @version Jun 2, 2014 5:53:00 PM
	 */
	private String denormalized(String significand, int sLength, int eLength, char sign) {
		String fraction = fractionTrueValue(significand, sLength);
		return (sign == '0' ? "" : "-") + Double.toString(Math.pow(2, 2 - Math.pow(2, eLength - 1)) * Double.parseDouble(fraction));
	}

	/**
	 * 用于计算小数【补码形式】部分的真值
	 * @param significand
	 *            小数部分的补码
	 * @param sLength
	 *            有效数的长度
	 * @return 小数部分的真值
	 * @author cylong
	 * @version Jun 2, 2014 7:27:28 PM
	 */
	private String fractionTrueValue(String significand, int sLength) {
		char[] significandArr = significand.toCharArray();
		double fraction = significandArr[sLength - 1] - '0';
		for(int i = 1; i < sLength; i++) {
			fraction = fraction / 2 + (significandArr[sLength - i - 1] - '0');
		}
		return Double.toString(fraction / 2);
	}

	/**
	 * 该方法用于模拟取反操作
	 * @param operand
	 *            操作数（补码表示）
	 * @return 计算结果（补码表示）
	 * @author cylong
	 * @version May 21, 2014 7:32:52 PM
	 */
	public String Negation(String operand) {
		char[] charArr = operand.toCharArray();
		for(int i = 0; i < charArr.length; i++) {
			charArr[i] = (char)(((charArr[i] - '0') ^ 1) + '0');
		}
		return new String(charArr);
	}

	/**
	 * 该方法用于模拟全加器，对两位及进位进行加法运算
	 * @param x
	 *            相加的一位
	 * @param y
	 *            相加的一位
	 * @param c
	 *            后一位的进位
	 * @return 长度为2 的字符串，从左向右，第1 位为和，第2位为进位
	 * @author cylong
	 * @version May 21, 2014 7:51:22 PM
	 */
	public String FullAdder(char x, char y, char c) {
		int sum = (x - '0') ^ (y - '0') ^ (c - '0');
		int carry = ((x - '0') & (y - '0')) | (((x - '0') | (y - '0')) & (c - '0'));
		return Integer.toString(sum) + Integer.toString(carry);
	}

	/**
	 * 该方法用于模拟进位先行加法器，要求调用 FullAdder 方法实现 <br>
	 * @param operand1
	 *            被加数，用补码表示
	 * @param operand2
	 *            加数，用补码表示
	 * @param c
	 *            后面的进位
	 * @param length
	 *            存放操作数的寄存器的长度，length 不小于操作数的长度，当某个操作数的长度小于 length
	 *            时，需要在高位补符号位。length 的取值不大 于 8。
	 * @return 长度为 length+1 的字符串。从左向右，前 length 位为计算结果， 用补码表示；最后 1 位为进位。
	 * @author cylong
	 * @version May 22, 2014 1:46:46 AM
	 */
	public String CLAAdder(String operand1, String operand2, char c, int length) {
		operand1 = extendBit(operand1, length);
		operand2 = extendBit(operand2, length);
		char[] charArr1 = operand1.toCharArray();
		char[] charArr2 = operand2.toCharArray();
		char[] charP = getCharP(charArr1, charArr2);
		char[] charG = getCharG(charArr1, charArr2);
		char[] charC = getCharC(charP, charG, c);
		return new String(getResult(charArr1, charArr2, charC));
	}

	/**
	 * @param charArr1
	 * @param charArr2
	 * @param charC
	 * @return 长度为 length+1 的字符串。从左向右，前 length 位为计算结果， 用补码表示；最后 1 位为进位。
	 * @author cylong
	 * @version May 22, 2014 5:33:18 AM
	 */
	private char[] getResult(char[] charArr1, char[] charArr2, char[] charC) {
		char[] result = new char[charArr1.length + 1];
		result[charArr1.length] = charC[0];
		for(int i = 0; i < result.length - 1; i++) {
			result[i] = FullAdder(charArr1[i], charArr2[i], charC[i + 1]).charAt(0);
		}
		return result;
	}

	/**
	 * @param charArr1
	 * @param charArr2
	 * @return 由 Pi = Xi | Yi 得到的数组
	 * @author cylong
	 * @version May 22, 2014 2:36:01 AM
	 */
	private char[] getCharP(char[] charArr1, char[] charArr2) {
		char[] charP = new char[charArr1.length];
		for(int i = 0; i < charP.length; i++) {
			charP[i] = (char)(((charArr1[i] - '0') | (charArr2[i] - '0')) + '0');
		}
		return charP;
	}

	/**
	 * @param charArr1
	 * @param charArr2
	 * @return 由 Pi = Xi & Yi 得到的数组
	 * @author cylong
	 * @version May 22, 2014 2:42:30 AM
	 */
	private char[] getCharG(char[] charArr1, char[] charArr2) {
		char[] charG = new char[charArr1.length];
		for(int i = 0; i < charG.length; i++) {
			charG[i] = (char)(((charArr1[i] - '0') & (charArr2[i] - '0')) + '0');
		}
		return charG;
	}

	/**
	 * @param charP
	 * @param charG
	 * @param c
	 *            后面的进位
	 * @return 由 Ci = Xi & Yi + (Xi | Yi) & Ci-1
	 * @author cylong
	 * @version May 22, 2014 5:25:31 AM
	 */
	private char[] getCharC(char[] charP, char[] charG, char c) {
		char[] charC = new char[charP.length + 1];
		charC[charP.length] = c;

		/** 此部分能够计算任意长度 */
		for(int i = 0; i < charC.length - 1; i++) {
			int index = charC.length - i - 2;
			charC[index] = (char)(((charC[index + 1] - '0') & (charP[index] - '0') | (charG[index] - '0')) + '0');
		}
		/** 以下代码为进位先行的代码，由于只能为8的倍数，故舍掉 */
		/**
		 * <pre>
		 * boolean[] booleanP = new boolean[charP.length];
		 * boolean[] booleanG = new boolean[charP.length];
		 * for (int i = 0; i &lt; charP.length; i++) {
		 * booleanP[i] = charP[i] == '1';
		 * booleanG[i] = charG[i] == '1';
		 * }
		 * switch (charP.length) {
		 * case 8:
		 * charC[0] = (booleanG[0] | (booleanP[0] &amp; booleanG[1]) | (booleanP[0] &amp; booleanP[1] &amp;
		 * booleanG[2]) | (booleanP[0] &amp; booleanP[1] &amp; booleanP[2] &amp; booleanG[3])
		 * | (booleanP[0] &amp; booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanG[4]) |
		 * (booleanP[0] &amp; booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp;
		 * booleanG[5])
		 * | (booleanP[0] &amp; booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp;
		 * booleanP[5] &amp; booleanG[6])
		 * | (booleanP[0] &amp; booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp;
		 * booleanP[5] &amp; booleanP[6] &amp; booleanG[7]) | (booleanP[0] &amp; booleanP[1] &amp; booleanP[2]
		 * &amp; booleanP[3] &amp; booleanP[4]
		 * &amp; booleanP[5] &amp; booleanP[6] &amp; booleanP[7] &amp; charC[charP.length] == '1')) ? '1' :
		 * '0';
		 * case 7:
		 * charC[1] = (booleanG[1] | (booleanP[1] &amp; booleanG[2]) | (booleanP[1] &amp; booleanP[2] &amp;
		 * booleanG[3]) | (booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanG[4])
		 * | (booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp; booleanG[5]) |
		 * (booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp;
		 * booleanG[6])
		 * | (booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp;
		 * booleanP[6] &amp; booleanG[7]) | (booleanP[1] &amp; booleanP[2] &amp; booleanP[3] &amp; booleanP[4]
		 * &amp; booleanP[5] &amp; booleanP[6]
		 * &amp; booleanP[7] &amp; charC[charP.length] == '1')) ? '1' : '0';
		 * case 6:
		 * charC[2] = (booleanG[2] | (booleanP[2] &amp; booleanG[3]) | (booleanP[2] &amp; booleanP[3] &amp;
		 * booleanG[4]) | (booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp; booleanG[5])
		 * | (booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp; booleanG[6]) |
		 * (booleanP[2] &amp; booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp; booleanP[6] &amp;
		 * booleanG[7]) | (booleanP[2] &amp; booleanP[3]
		 * &amp; booleanP[4] &amp; booleanP[5] &amp; booleanP[6] &amp; booleanP[7] &amp; charC[charP.length]
		 * == '1')) ? '1' : '0';
		 * case 5:
		 * charC[3] = (booleanG[3] | (booleanP[3] &amp; booleanG[4]) | (booleanP[3] &amp; booleanP[4] &amp;
		 * booleanG[5]) | (booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp; booleanG[6])
		 * | (booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp; booleanP[6] &amp; booleanG[7]) |
		 * (booleanP[3] &amp; booleanP[4] &amp; booleanP[5] &amp; booleanP[6] &amp; booleanP[7] &amp;
		 * charC[charP.length] == '1')) ? '1' : '0';
		 * case 4:
		 * charC[4] = (booleanG[4] | (booleanP[4] &amp; booleanG[5]) | (booleanP[4] &amp; booleanP[5] &amp;
		 * booleanG[6]) | (booleanP[4] &amp; booleanP[5] &amp; booleanP[6] &amp; booleanG[7]) | (booleanP[4]
		 * &amp; booleanP[5]
		 * &amp; booleanP[6] &amp; booleanP[7] &amp; charC[charP.length] == '1')) ? '1' : '0';
		 * case 3:
		 * charC[5] = (booleanG[5] | (booleanP[5] &amp; booleanG[6]) | (booleanP[5] &amp; booleanP[6] &amp;
		 * booleanG[7]) | (booleanP[5] &amp; booleanP[6] &amp; booleanP[7] &amp; charC[charP.length] == '1'))
		 * ? '1' : '0';
		 * case 2:
		 * charC[6] = (booleanG[6] | (booleanP[6] &amp; booleanG[7]) | (booleanP[6] &amp; booleanP[7] &amp;
		 * charC[charP.length] == '1')) ? '1' : '0';
		 * case 1:
		 * charC[7] = (booleanG[7] | (booleanP[7] &amp; (charC[charP.length] == '1'))) ? '1' : '0';
		 * }
		 * </pre>
		 */
		return charC;
	}

	/**
	 * 该方法用于模拟部分进位先行加法器，要求调用 CLAAdder 方法来模拟 8 位的进位先行加法器，并实现其串联
	 * @param operand1
	 *            被加数，用补码表示
	 * @param operand2
	 *            加数，用补码表示
	 * @param c
	 *            后面的进位
	 * @param length
	 *            存放操作数的寄存器的长度。length 不小于操作数的长度，当某个操作数的长度小于 length 时，需要在高位补符号位
	 * @return 长度为 length+1 的字符串。从左向右，前 length 位计算结果， 用补码表示；最后 1 位为是否溢出，其中溢出为
	 *         1，不溢出为 0
	 * @author cylong
	 * @version May 22, 2014 5:50:57 AM
	 */
	public String Addition(String operand1, String operand2, char c, int length) {
		operand1 = extendBit(operand1, length);
		operand2 = extendBit(operand2, length);
		int maxBit = 8;
		String[] operandArr1 = getSplit(operand1, length, maxBit);
		String[] operandArr2 = getSplit(operand2, length, maxBit);
		StringBuilder builder = new StringBuilder();
		for(int i = operandArr1.length - 1; i >= 0; i--) {
			builder.insert(0, CLAAdder(operandArr1[i], operandArr2[i], c, operandArr1[i].length()).substring(0, operandArr1[i].length()));
			c = CLAAdder(operandArr1[i], operandArr2[i], c, operandArr1[i].length()).charAt(operandArr1[i].length());
		}
		// 判断是否溢出
		if (operand1.charAt(0) == operand2.charAt(0) && operand1.charAt(0) != builder.charAt(0)) {
			builder.append('1');
		} else {
			builder.append('0');
		}
		return builder.toString();
	}

	/**
	 * @param operand
	 *            二进制操作数
	 * @param length
	 *            操作数长度
	 * @param maxBit
	 *            每一段的最大位数
	 * @return 分段后的字符串数组
	 * @author cylong
	 * @version May 22, 2014 7:42:38 AM
	 */
	private String[] getSplit(String operand, int length, int maxBit) {
		String[] operandArr = new String[(length + maxBit - 1) / maxBit];
		for(int i = 0; i < operandArr.length - 1; i++) {
			operandArr[i] = operand.substring(i * maxBit, (i + 1) * maxBit);
		}
		operandArr[operandArr.length - 1] = operand.substring((operandArr.length - 1) * maxBit);
		return operandArr;
	}

	/**
	 * 该方法用于模拟浮点数的加法，要求调用 Addition、Subtraction 方法来实现 <br>
	 * @param operand1
	 *            被加数，用二进制表示
	 * @param operand2
	 *            加数，用二进制表示
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param gLength
	 *            保护位的长度
	 * @return 长度为 1+sLength+eLength+1 的字符串。从左向右，从左向右依 次为符号、指数（移码表示）、尾数（首位隐藏）；最后
	 *         1 位为是否溢出， 其中溢出为 1，不溢出为 0。舍入采用就近舍入，其中 10…0 时进位
	 * @author cylong
	 * @version Jun 2, 2014 9:14:35 PM
	 */
	public String AdditionF(String operand1, String operand2, int sLength, int eLength, int gLength) {
		char sign1 = operand1.charAt(0);
		char sign2 = operand2.charAt(0);
		String exp1 = "0" + operand1.substring(1, eLength + 1);
		String exp2 = "0" + operand2.substring(1, eLength + 1);
		String significand1 = "01" + operand1.substring(eLength + 1);
		String significand2 = "01" + operand2.substring(eLength + 1);
		String zero = extendBit("0", sLength + eLength);
		char overflow = '0';
		// 检查0
		if (operand1.substring(1).equals(zero)) {
			return operand2 + overflow;
		} else if (operand2.substring(1).equals(zero)) { return operand1 + overflow; }
		// 添加保护位
		significand1 = addGuardBits(significand1, gLength);
		significand2 = addGuardBits(significand2, gLength);
		// 对齐有效数
		if (exp1.compareTo(exp2) < 0) {
			significand1 = logicalRightShift(significand1, Integer.parseInt(integerTrueValue(Subtraction(exp2, exp1, exp1.length()).substring(0, exp1.length()))));
			exp1 = exp2;
		} else {
			significand2 = logicalRightShift(significand2, Integer.parseInt(integerTrueValue(Subtraction(exp1, exp2, exp1.length()).substring(0, exp1.length()))));
			exp2 = exp1;
		}
		// 加或者减有效数
		String significandResult = null;
		if (sign1 == sign2) {
			significandResult = Addition(significand1, significand2, '0', significand1.length()).substring(0, significand1.length());
			// 判断有效数是否上溢
			if (significandResult.charAt(0) == '1') {
				significandResult = logicalRightShift(significandResult, 1);
				exp1 = exp2 = Addition(exp1, "01", '0', exp1.length()).substring(0, exp1.length());
				// 判断指数是否上溢
				if (exp1.substring(1).equals(extendBit("1", eLength))) {
					overflow = '1';
				}
			}
		} else {
			if (sign1 == '0') {
				significandResult = Subtraction(significand1, significand2, significand1.length()).substring(0, significand1.length());
			} else if (sign2 == '0') {
				sign1 = sign2;
				significandResult = Subtraction(significand2, significand1, significand1.length()).substring(0, significand1.length());
			}
			// 判断有效数是否小于0
			if (significandResult.charAt(0) == '1') {
				significandResult = Addition(Negation(significandResult), "01", '0', significandResult.length()).substring(0, significandResult.length());
				// 符号位取反
				sign1 = (char)(((sign1 - '0') ^ 1) + '0');
			}
		}
		// 去除result的首位【首位是为了判断有效数是否上溢或者下溢】
		significandResult = significandResult.substring(1);
		// 规格化结果
		while(significandResult.charAt(0) == '0') {
			significandResult = LeftShift(significandResult, 1);
			exp1 = exp2 = Subtraction(exp1, "01", exp1.length()).substring(0, exp1.length());
			// 判断指数是否下溢
			if (exp1.charAt(0) == '1') { return sign1 + extendBit("0", sLength + eLength); }
		}
		// 结果舍入处理
		String[] roundResult = round(sign1, exp1, eLength, significandResult, sLength, gLength);
		if (roundResult.length == 2) {
			return sign1 + roundResult[0].substring(1) + roundResult[1] + overflow;
		} else {
			return roundResult[0];
		}
	}

	/**
	 * 舍入处理【就近舍入】
	 * @param sign1
	 *            符号
	 * @param eLength
	 *            指数长度
	 * @param exp
	 *            指数
	 * @param significandResult
	 *            操作数
	 * @param sLength
	 *            尾数的长度
	 * @param gLength
	 *            保护位长度
	 * @return 处理后的结果 分别是指数和尾数，若指数上溢则返回无穷
	 * @author cylong
	 * @version Jun 9, 2014 12:12:12 AM
	 */
	private String[] round(char sign1, String exp, int eLength, String significandResult, int sLength, int gLength) {
		String guardBitsMediumValue = getGuardBitsMediumValue(gLength);
		String expMax = extendBit("1", eLength);
		String tempResult = "0" + significandResult.substring(0, significandResult.length() - gLength);
		if (significandResult.substring(1 + sLength).compareTo(guardBitsMediumValue) >= 0) {
			significandResult = Addition(tempResult, "01", '0', tempResult.length()).substring(0, tempResult.length());
			// 判断是否进位[增加指数]
			// 有效数右移
			if (significandResult.charAt(0) == '1') {
				significandResult = logicalRightShift(significandResult, 1);
				exp = Addition(exp, "01", '0', exp.length()).substring(0, exp.length());
				// 判断指数是否上溢
				if (exp.equals(expMax)) { return new String[]{sign1 + expMax + extendBit("0", sLength)}; }
			}
		} else {
			significandResult = tempResult;
		}
		return new String[]{exp, significandResult.substring(2)};
	}

	/**
	 * 计算保护位的中值
	 * @param gLength
	 *            保护位的长度
	 * @return 100...0
	 * @author cylong
	 * @version Jun 8, 2014 10:26:11 PM
	 */
	private String getGuardBitsMediumValue(int gLength) {
		StringBuilder mediumValue = new StringBuilder("1");
		for(int i = 0; i < gLength - 1; i++) {
			mediumValue.append('0');
		}
		return mediumValue.toString();
	}

	/**
	 * 添加保护位
	 * @param significand
	 *            操作数
	 * @param gLength
	 *            保护位长度
	 * @return 添加了保护位后的操作数
	 * @author cylong
	 * @version Jun 8, 2014 12:29:28 PM
	 */
	private String addGuardBits(String significand, int gLength) {
		StringBuilder result = new StringBuilder(significand);
		for(int i = 0; i < gLength; i++) {
			result.append('0');
		}
		return result.toString();
	}

	/**
	 * 该方法用于模拟十进制整数加法，要求调用 Addition 方法来实现。
	 * @param operand1
	 *            被加数，用 NBCD 码表示
	 * @param operand2
	 *            加数，用 NBCD 码表示
	 * @return 和的 NBCD 码表示
	 * @author cylong
	 * @version Jun 13, 2014 4:40:37 PM
	 */
	public String AdditionD(String operand1, String operand2) {
		if (!operand1.substring(0, 4).equals(operand2.substring(0, 4))) { return SubtractionD(operand1, negativeDecimal(operand2)); }
		String sign = operand1.substring(0, 4);
		String result = AddOrSub(operand1, operand2, '0');
		// 将-0转化为0
		if (result.equals("0000")) { return "11000000"; }
		return sign + (result.substring(0, 4).equals("0000") ? result.substring(4) : result);
	}

	/**
	 * @param operand1
	 *            被加数或被减数
	 * @param operand2
	 *            加数或减数
	 * @param type
	 *            '0'为加，'1'为减
	 * @return 返回结果
	 * @author cylong
	 * @version Jun 13, 2014 11:25:00 PM
	 */
	private String AddOrSub(String operand1, String operand2, char type) {
		int length = operand1.length() > operand2.length() ? operand1.length() : operand2.length();
		operand1 = extendBit("0" + operand1.substring(4), length);
		operand2 = extendBit("0" + operand2.substring(4), length);
		String[] opArray1 = getSplit(operand1, operand1.length(), 4);
		String[] opArray2 = getSplit(operand2, operand2.length(), 4);
		opArray2 = type == '0' ? opArray2 : negativeAndAddTen(operand2);
		String[] result = new String[opArray1.length];
		String tempResult = FullAdderD(opArray1[opArray1.length - 1], opArray2[opArray2.length - 1], type);
		char carry = tempResult.charAt(0);
		result[opArray1.length - 1] = tempResult.substring(1);
		for(int i = opArray1.length - 2; i >= 0; i--) {
			tempResult = FullAdderD(opArray1[i], opArray2[i], carry);
			result[i] = tempResult.substring(1);
			carry = tempResult.charAt(0);
		}
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < result.length; i++) {
			resultBuilder.append(result[i]);
		}
		return resultBuilder.toString();
	}

	/**
	 * 对NDBC表示的数取相反数
	 * @param operand
	 *            要取反的数
	 * @author cylong 取反后的数
	 * @version Jun 13, 2014 8:56:56 PM
	 */
	private String negativeDecimal(String operand) {
		return (operand.substring(0, 4).equals("1100") ? "1101" : "1100") + operand.substring(4);
	}

	/**
	 * 该方法用于对十进制的每一位进行计算。用NDBC表示
	 * @param x
	 *            相加的四位
	 * @param y
	 *            相加的四位
	 * @param c
	 *            后一位的进位
	 * @return 长度为5的字符串， 第一位为进位，后面为和
	 * @author cylong
	 * @version May 21, 2014 7:51:22 PM
	 */
	private String FullAdderD(String operand1, String operand2, char c) {
		String result = Addition("0" + operand1, "0" + operand2, c, 8).substring(3, 8);
		if (result.compareTo("01001") > 0) {
			result = Addition(result, "00110", '0', 5).substring(0, 5);
		}
		return result;
	}

	/**
	 * @param operand1
	 *            被减数，用补码表示
	 * @param operand2
	 *            减数，用补码表示
	 * @param length
	 *            存放操作数的寄存器的长度。length 不小于操作数的长度，当某个操作数的长度小于 length 时，需要在高位补符号位
	 * @return 长度为 length+1 的字符串。从左向右，前 length 位计算结果， 用补码表示；最后 1 位为是否溢出，其中溢出为
	 *         1，不溢出为 0
	 * @author cylong
	 * @version May 22, 2014 9:41:30 AM
	 */
	public String Subtraction(String operand1, String operand2, int length) {
		return Addition(operand1, Negation(operand2), '1', length);
	}

	/**
	 * 该方法用于模拟浮点数的减法，要求调用 AdditionF 方法来实现
	 * @param operand1
	 *            被减数，用二进制表示
	 * @param operand2
	 *            减数，用二进制表示
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param gLength
	 *            保护位的长度
	 * @return 长度为 1+sLength+eLength+1 的字符串。从左向右，依次为符号、 指数（移码表示）、尾数（首位隐藏）；最后 1
	 *         位为是否溢出，其中溢出为 1，不溢出为 0。舍入采用就近舍入，其中 10…0 时进位
	 * @author cylong
	 * @version Jun 8, 2014 10:07:59 PM
	 */
	public String SubtractionF(String operand1, String operand2, int sLength, int eLength, int gLength) {
		return AdditionF(operand1, ((operand2.charAt(0) - '0') ^ 1) + operand2.substring(1), sLength, eLength, gLength);
	}

	/**
	 * 该方法用于模拟十进制数减法，要求调用 Negation、Addition 方法来实现
	 * @param operand1
	 *            被减数，用 NBCD 码表示
	 * @param operand2
	 *            减数，用 NBCD 码表示
	 * @return 差的 NBCD 码表示
	 * @author cylong
	 * @version Jun 13, 2014 6:49:54 PM
	 */
	public String SubtractionD(String operand1, String operand2) {
		if (!operand1.substring(0, 4).equals(operand2.substring(0, 4))) { return AdditionD(operand1, negativeDecimal(operand2)); }
		String sign = operand1.substring(0, 4);
		String result = AddOrSub(operand1, operand2, '1');
		String[] resArray = null;
		boolean negative = false;
		// 判断是否有进位【就是被减数绝的对值是否小于减数的绝对值】
		// 我扩展了位数，第一位前补0，这样取反加10后变成了1001，所以没有进位的时候是1001，而不是0000
		if (negative = result.substring(0, 4).equals("1001")) {
			// 所有位取反
			resArray = negativeAndAddTen(result);
			StringBuilder resArrBuilder = new StringBuilder();
			for(int i = 0; i < resArray.length; i++) {
				resArrBuilder.append(resArray[i]);
			}
			// 取反后加一
			result = AddOrSub(resArrBuilder.toString(), "11000001", '0');
		} else {
			resArray = getSplit(result, result.length(), 4);
		}
		// 去掉前面的0
		for(int i = 0; i < resArray.length - 1; i++) {
			if (!result.substring(0, 4).equals("0000")) {
				break;
			}
			result = result.substring(4);
		}
		// 将-0转化为0
		if (result.equals("0000")) { return "11000000"; }
		return (negative ? negativeAndAddTen(sign)[0] : sign) + result;
	}

	/**
	 * 对操作数每四位取反加10， 并分成每四位一组
	 * @param operand
	 *            NBCD表示的二进制数
	 * @return 分组后的字符串数组
	 * @author cylong
	 * @version Jun 13, 2014 10:38:44 PM
	 */
	private String[] negativeAndAddTen(String operand) {
		String[] opArray = getSplit(operand, operand.length(), 4);
		// 每位取反加10
		for(int i = 0; i < opArray.length; i++) {
			opArray[i] = Addition("0" + Negation(opArray[i]), "01010", '0', 8).substring(4, 8);
		}
		return opArray;
	}

	/**
	 * 该方法用于模拟 Booth 乘法，要求调用 Addition 方法和 Subtraction 方法来实现
	 * @param operand1
	 *            被乘数，用补码表示
	 * @param operand2
	 *            乘数，用补码表示
	 * @param length
	 *            存放操作数的寄存器的长度。length 不小于操作数的长度，当某个操作数的长度小于 length 时，需要在高位补符号位
	 * @return 长度为 length×2，为计算结果，用补码表示
	 * @author cylong
	 * @version May 22, 2014 9:49:30 AM
	 */
	public String Multiplication(String operand1, String operand2, int length) {
		operand1 = extendBit(operand1, length << 1);
		operand2 = extendBit(operand2, length << 1) + "0";
		String result = "0";
		for(int i = 0; i < length; i++) {
			switch(operand2.charAt((length << 1) - i) - operand2.charAt((length << 1) - i - 1)) {
			case 1:
				result = Addition(result, LeftShift(operand1, i), '0', length << 1).substring(0, length << 1);
				break;
			case -1:
				result = Subtraction(result, LeftShift(operand1, i), length << 1).substring(0, length << 1);
				break;
			}
		}
		return result;
	}

	/**
	 * 该方法用于模拟浮点数的乘法，要求调用 Addition、Subtraction 方法来实现
	 * @param operand1
	 *            被乘数，用补码表示
	 * @param operand2
	 *            乘数，用补码表示
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @return 长度为 1+sLength+eLength，为积，用二进制表示。从左向右，
	 *         依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入采用就近舍入， 其中 10…0 时进位
	 * @author cylong
	 * @version Jun 8, 2014 11:08:13 PM
	 */
	public String MultiplicationF(String operand1, String operand2, int sLength, int eLength) {
		String zero = extendBit("0", sLength + eLength);
		// 判断操作数是否为0
		if (operand1.substring(1).equals(zero) || operand2.substring(1).equals(zero)) { return extendBit("0", 1 + sLength + eLength); }
		char sign1 = operand1.charAt(0);
		char sign2 = operand2.charAt(0);
		String exp1 = "00" + operand1.substring(1, eLength + 1);
		String exp2 = "00" + operand2.substring(1, eLength + 1);
		String expBias = Complement(Integer.toString((int)(Math.pow(2, eLength - 1) - 1)), exp2.length());
		String expMax = extendBit("1", eLength);
		String significand1 = "01" + operand1.substring(eLength + 1);
		String significand2 = "1" + operand2.substring(eLength + 1);
		String expResult = Subtraction(Addition(exp1, exp2, '0', exp2.length()).substring(0, exp2.length()), expBias, exp2.length()).substring(0, exp2.length());
		// 计算符号
		sign1 = (char)((sign1 - '0') ^ (sign2 - '0') + '0');
		// 判断指数是否溢出
		if (expResult.charAt(0) == '0' && expResult.charAt(1) == '1') {
			return sign1 + expMax + extendBit("0", sLength);
		} else if (expResult.charAt(0) == '1' && expResult.charAt(1) == '1') { return extendBit("0", 1 + sLength + eLength); }
		String significandResult = extendBit("0", sLength + 2);
		String overall = significandResult + significand2;
		for(int i = 0; i < sLength + 1; i++) {
			if (significand2.charAt(significand2.length() - 1) == '1') {
				significandResult = Addition(significandResult, significand1, '0', significandResult.length()).substring(0, significandResult.length());
				overall = significandResult + significand2;
			}
			overall = logicalRightShift(overall, 1);
			significandResult = overall.substring(0, significandResult.length());
			significand2 = overall.substring(significandResult.length());
		}
		// 去除result的首位【首位是为了判断有效数有没有上溢】
		significandResult = significandResult.substring(1);
		// 判断尾数是否上溢
		if (significandResult.charAt(0) == '1') {
			overall = logicalRightShift(overall, 1);
			expResult = Addition(expResult, "01", '0', expResult.length()).substring(0, expResult.length());
			// 判断指数是否上溢
			if (expResult.substring(2).equals(expMax)) { return sign1 + expMax + extendBit("0", sLength); }
		}
		return sign1 + expResult.substring(2) + overall.substring(3, 3 + sLength);
	}

	/**
	 * 该方法用于模拟不恢复余数除法，要求调用 Addition 方法和 Subtraction 方法 来实现
	 * @param operand1
	 *            被除数，用补码表示
	 * @param operand2
	 *            除数，用补码表示
	 * @param length
	 *            存放操作数的寄存器的长度
	 * @return 长度为 length×2 的字符串。从左向右，前 length 位为商，用补码表示；后 length 为余数，用补码表示
	 * @author cylong
	 * @version May 23, 2014 4:04:01 AM
	 */
	public String Division(String operand1, String operand2, int length) {
		String dividend = extendBit(operand1, length);
		String quotient = dividend;
		String remainder = extendBit(operand1, length << 1).substring(0, length);
		String divisor = extendBit(operand2, length);
		String zero = extendBit("0", length);
		boolean remainderIsZero = false; // 判断余数是否为0
		// 如果被除数为0，直接return
		if (dividend.equals(zero)) { return extendBit("0", length << 1); }
		for(int i = 0; i < length + 1; i++) {
			// 若余数不为0，如果除数和余数符号不同，则余数加上除数，相同则减去除数
			// 若余数为0，则不操作余数
			if (!remainderIsZero) {
				if (divisor.charAt(0) == remainder.charAt(0)) {
					remainder = Subtraction(remainder, divisor, length).substring(0, length);
				} else {
					remainder = Addition(remainder, divisor, '0', length).substring(0, length);
				}
				// 若余数不为0，得到的余数和除数符号相同，则在商后面加1；符号不同则在商后面加0
				// 若余数为0， 若除数为正，商补0，否则补1
				if (divisor.charAt(0) == remainder.charAt(0)) {
					quotient += "1";
				} else {
					quotient += "0";
				}
			} else {
				if (divisor.charAt(0) == '0') {
					quotient += "0";
				} else {
					quotient += "1";
				}
			}
			// 若余数不为0，余数和商整体左移一位，最后一次余数不左移
			// 若余数为0， 则不再操作余数
			if (i == length || remainderIsZero) {
				quotient = LeftShift(quotient, 1).substring(0, length);
			} else {
				remainder = LeftShift(remainder + quotient, 1).substring(0, length);
				quotient = LeftShift(remainder + quotient, 1).substring(length, length << 1);
			}
			// 判断余数是否为0
			if (remainder.equals(zero)) {
				// remainderIsZero = true;
			}
		}
		// 若余数为0，最后若除数为负，商加1
		if (remainderIsZero) {
			if (divisor.charAt(0) == '1') {
				quotient = Addition(quotient, "01", '0', length).substring(0, length);
			}
			// 如果余数为0，则不执行下面的代码
			return quotient + remainder;
		}
		// 最后如果商是负的，则商加1（数值加一）；否则不加
		if (quotient.charAt(0) == '1') {
			quotient = Addition(quotient, "01", '0', length).substring(0, length);
		}
		// 如果余数和被除数符号不同
		if (remainder.charAt(0) != dividend.charAt(0)) {
			// 如果被除数和除数符号相同则余数加上除数；符号相反则减去除数
			if (dividend.charAt(0) == divisor.charAt(0)) {
				remainder = Addition(remainder, divisor, '0', length).substring(0, length);
			} else {
				remainder = Addition(remainder, divisor, '0', length).substring(0, length);
			}
		}
		return quotient + remainder;
	}

	/**
	 * 该方法用于模拟浮点数的恢复余数除法，要求调用 Addition、Subtraction 方法 来实现
	 * @param operand1
	 *            被除数，用补码表示
	 * @param operand2
	 *            除数，用补码表示
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @return 长度为 1+sLength+eLength，为商，用二进制表示。从左向右，
	 *         依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入采用就近舍入， 其中 10…0 时进位
	 * @author cylong
	 * @version Jun 9, 2014 5:46:45 AM
	 */
	public String DivisionF(String operand1, String operand2, int sLength, int eLength) {
		char sign1 = operand1.charAt(0);
		char sign2 = operand2.charAt(0);
		// 计算符号
		sign1 = (char)((sign1 - '0') ^ (sign2 - '0') + '0');
		String expMax = extendBit("1", eLength);
		String zero = extendBit("0", sLength + eLength);
		// 判断操作数是否为0
		if (operand1.substring(1).equals(zero)) {
			return sign2 + extendBit("0", sLength + eLength);
		} else if (operand2.substring(1).equals(zero)) { return sign1 + expMax + extendBit("0", sLength); }
		String exp1 = "00" + operand1.substring(1, eLength + 1);
		String exp2 = "00" + operand2.substring(1, eLength + 1);
		String expBias = Complement(Integer.toString((int)(Math.pow(2, eLength - 1) - 1)), exp2.length());
		String expResult = Addition(Subtraction(exp1, exp2, exp2.length()).substring(0, exp2.length()), expBias, '0', exp2.length()).substring(0, exp2.length());
		// 判断指数是否溢出
		if (expResult.charAt(0) == '0' && expResult.charAt(1) == '1') {
			return sign1 + expMax + extendBit("0", sLength);
		} else if (expResult.charAt(0) == '1' && expResult.charAt(1) == '1') { return sign1 + extendBit("0", sLength + eLength); }
		// 除数和余数前面都额外加0是为了防止被除数小于除数导致结果是0
		String remainder = "01" + operand1.substring(eLength + 1);
		String divisor = "01" + operand2.substring(eLength + 1);
		String quotient = extendBit("0", sLength + 1);
		String RAndQ = remainder + quotient;
		for(int i = 0; i < sLength + 1; i++) {
			if (remainder.compareTo(divisor) < 0) {
				RAndQ = RAndQ.substring(1) + "0";
			} else {
				remainder = Subtraction(remainder, divisor, remainder.length()).substring(0, remainder.length());
				RAndQ = (remainder + quotient).substring(1) + "1";
			}
			remainder = RAndQ.substring(0, sLength + 2);
			quotient = RAndQ.substring(sLength + 2);
		}
		// 规格化结果
		while(quotient.charAt(0) == '0') {
			quotient = LeftShift(quotient, 1);
			expResult = Subtraction(expResult, "01", expResult.length()).substring(0, expResult.length());
			// 判断指数是否下溢
			if (expResult.charAt(0) == '1') { return sign1 + extendBit("0", sLength + eLength); }
		}
		return sign1 + expResult.substring(2) + quotient.substring(1);
	}

	/**
	 * 该方法用于模拟两个整数或两个浮点数的四则运算，要求调用 Addition 方法、 Subtraction 方法、Multiplication
	 * 方法、Division 方法、AdditionF 方法、SubtractionF 方法、MultiplicationF 方法和 DivisionF
	 * 方法。目前只需要实现 type 为 INTEGER 和 FLOAT 时的情形
	 * @param number1
	 *            操作数 1，用十进制表示，为被加数/被减数/被乘数/被除数
	 * @param number2
	 *            操作数 2，用十进制表示，为加数/减数/乘数/除数
	 * @param type
	 *            操作数类型
	 * @param operation
	 *            操作类型 <div>
	 *            <ul>
	 *            <li>当 type 为 INTEGER 时：包含 ADDITION，SUBTRACTION，MULTIPLICATION，
	 *            DIVISION</li>
	 *            <li>当 type 为 FLOAT 时：包含 ADDITION，SUBTRACTION，MULTIPLICATION，
	 *            DIVISION</li>
	 *            <li>当 type 为 DECIMAL 时：ADDITION，SUBTRACTION</li>
	 *            <ul>
	 *            </div>
	 * @param length
	 *            存放长度信息的数组 <div>
	 *            <ul>
	 *            <li>当 type 为 INTEGER 时：包含 1 个元素，操作数将会转换为该长度 的补码表示</li>
	 *            <li>当 type 为 FLOAT 时：包含 3 个元素，依次为尾数长度、指数长度和
	 *            保护位长度（乘法和除法中保护位长度被自动忽略）</li>
	 *            <li>当 type 为 DECIMAL 时：忽略该参数</li>
	 *            <ul>
	 *            </div>
	 * @return 计算结果，用十进制表示。其中，加法返回和，减法返回差，乘法返回积，除法返回商。如果是负数，最左边为“-”；如果是正数或 0，不
	 *         需要符号位
	 * @author cylong
	 * @version May 22, 2014 4:13:11 PM
	 * @version Jun 14, 2014 2:16:26 AM
	 */
	public String Calculation(String number1, String number2, Type type, Operation operation, int[] length) {
		switch(type) {
		case INTEGER:
			number1 = Complement(number1, length[0]);
			number2 = Complement(number2, length[0]);
			switch(operation) {
			case ADDITION:
				return TrueValue(Addition(number1, number2, '0', length[0]).substring(0, length[0]), type, new int[0]);
			case SUBTRACTION:
				return TrueValue(Subtraction(number1, number2, length[0]).substring(0, length[0]), type, new int[0]);
			case MULTIPLICATION:
				return TrueValue(Multiplication(number1, number2, length[0]), type, new int[0]);
			case DIVISION:
				return TrueValue(Division(number1, number2, length[0]).substring(0, length[0]), type, new int[0]);
			}
		case FLOAT:
			number1 = FloatRepresentation(number1, length[0], length[1]);
			number2 = FloatRepresentation(number2, length[0], length[1]);
			switch(operation) {
			case ADDITION:
				return TrueValue(AdditionF(number1, number2, length[0], length[1], length[2]).substring(0, 1 + length[0] + length[1]), type, new int[]{length[0], length[1]});
			case SUBTRACTION:
				return TrueValue(SubtractionF(number1, number2, length[0], length[1], length[2]).substring(0, 1 + length[0] + length[1]), type, new int[]{length[0], length[1]});
			case MULTIPLICATION:
				return TrueValue(MultiplicationF(number1, number2, length[0], length[1]), type, new int[]{length[0], length[1]});
			case DIVISION:
				return TrueValue(DivisionF(number1, number2, length[0], length[1]), type, new int[]{length[0], length[1]});
			}
		case DECIMAL:
			number1 = NBCD(number1);
			number2 = NBCD(number2);
			switch(operation) {
			case ADDITION:
				return TrueValue(AdditionD(number1, number2), type, length);
			case SUBTRACTION:
				return TrueValue(SubtractionD(number1, number2), type, length);
			default:
				return null;
			}
		}
		return null;
	}

}
