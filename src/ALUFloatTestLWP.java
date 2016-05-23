/**
 * 
 * @author lwpeng
 * @version Jun 11, 2014 4:33:53 AM
 */
public class ALUFloatTestLWP {
	/*
	 * 这个不是JUnit
	 */
	public static void main (String[] args) {
		ALUFloatTestLWP.test(32, 1000, null, true);
	}
	/**
	 * @param length
	 *            【必须为32或者64，否则默认为64】
	 * @param total
	 *            总共测试的次数
	 * @param operation
	 *            操作类型，null为随机
	 * @param ignoreCarry
	 *            是否忽略进位带来的影响
	 * @author lwpeng
	 * @version Jun 11, 2014 4:37:00 AM
	 */
	public static void test (int length, int total, ALU.Operation operation, boolean ignoreCarry) {
		ALU alu = new ALU();

		int right = 0, sLength, eLength;
		float f1, f2;
		double d1, d2;
		String o1, o2, bOperand1, bOperand2, res, operand1, operand2, result;
		ALU.Operation op = operation;
		int[] detailTotal = new int[4];
		int[] detailRight = new int[4];
		int error = 0;

		switch (length) {
		case 32:
			sLength = 23;
			eLength = 8;
			break;
		default:
			length = 64;
			sLength = 52;
			eLength = 11;
			break;
		}

		long start = System.currentTimeMillis();
		for (int i = 0; i < total; i++) {
			try {
				if (operation == null)
					switch ((int) (Math.random() * 4)) {
					case 0:
						op = ALU.Operation.ADDITION;
						detailTotal[0]++;
						break;
					case 1:
						op = ALU.Operation.SUBTRACTION;
						detailTotal[1]++;
						break;
					case 2:
						op = ALU.Operation.MULTIPLICATION;
						detailTotal[2]++;
						break;
					default:
						op = ALU.Operation.DIVISION;
						detailTotal[3]++;
					}

				if (length == 32) {
					f1 = (float) (Math.random() * 10000 - 5000);
					f2 = (float) (Math.random() * 10000 - 5000);
					o1 = Float.toString(f1);
					o2 = Float.toString(f2);
					bOperand1 = Integer.toBinaryString(Float.floatToIntBits(f1));
					bOperand2 = Integer.toBinaryString(Float.floatToIntBits(f2));
					switch (op) {
					case ADDITION:
						res = Integer.toBinaryString(Float.floatToIntBits(f1 + f2));
						break;
					case SUBTRACTION:
						res = Integer.toBinaryString(Float.floatToIntBits(f1 - f2));
						break;
					case MULTIPLICATION:
						res = Integer.toBinaryString(Float.floatToIntBits(f1 * f2));
						break;
					default:
						res = Integer.toBinaryString(Float.floatToIntBits(f1 / f2));
					}
				} else {
					d1 = Math.random() * 10000 - 5000;
					d2 = Math.random() * 10000 - 5000;
					o1 = String.valueOf(d1);
					o2 = String.valueOf(d2);
					bOperand1 = Long.toBinaryString(Double.doubleToLongBits(d1));
					bOperand2 = Long.toBinaryString(Double.doubleToLongBits(d2));
					switch (op) {
					case ADDITION:
						res = Long.toBinaryString(Double.doubleToLongBits(d1 + d2));
						break;
					case SUBTRACTION:
						res = Long.toBinaryString(Double.doubleToLongBits(d1 - d2));
						break;
					case MULTIPLICATION:
						res = Long.toBinaryString(Double.doubleToLongBits(d1 * d2));
						break;
					default:
						res = Long.toBinaryString(Double.doubleToLongBits(d1 / d2));
					}
				}
				for (int j = bOperand1.length(); j < length; j++)
					bOperand1 = '0' + bOperand1;
				for (int j = bOperand2.length(); j < length; j++)
					bOperand2 = '0' + bOperand2;
				for (int j = res.length(); j < length; j++)
					res = '0' + res;

				operand1 = alu.IEEE754(o1, length);
				operand2 = alu.IEEE754(o2, length);

				switch (op) {
				case ADDITION:
					result = alu.AdditionF(operand1, operand2, sLength, eLength, 4).substring(0, length);
					break;
				case SUBTRACTION:
					result = alu.SubtractionF(operand1, operand2, sLength, eLength, 4).substring(0, length);
					break;
				case MULTIPLICATION:
					result = alu.MultiplicationF(operand1, operand2, sLength, eLength);
					break;
				default:
					result = alu.DivisionF(operand1, operand2, sLength, eLength);
				}

				boolean isEqual = res.equals(result);
				if (ignoreCarry) {
					if (!isEqual)
						isEqual |= alu.Addition(res, "01", '0', length).substring(0, length).equals(result) || alu.Addition(result, "01", '0', length).substring(0, length).equals(res);
				}
				if (isEqual) {
					right++;
					detailRight[op.ordinal()]++;
				} else {
					System.out.println(i + 1);
					System.out.println("ALU.Operation:\t" + op);
					System.out.println("operand1: \t" + bOperand1 + " " + o1);
					System.out.println("yours:    \t" + operand1);
					System.out.println("operand2: \t" + bOperand2 + " " + o2);
					System.out.println("yours:    \t" + operand2);
					System.out.println("expected: \t" + res);
					System.out.println("result:   \t" + result);
					System.out.println();
				}
			} catch (Exception e) {
				error++;
			}
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("result");
		if (operation != null)
			System.out.println("ALU.Operation:\t" + operation);
		System.out.println("time:     \t" + time / 1000.0 + "s");
		System.out.println("total:    \t" + total);
		System.out.println("right:    \t" + right);
		System.out.println("error:    \t" + error);
		System.out.println("wrong:    \t" + (total - right));
		System.out.println("accuracy: \t" + String.format("%.2f", right * 100.0 / total) + "%");
		System.out.println();
		if (operation == null) {
			System.out.println("detail:");
			for (int i = 0; i < 4; i++) {
				switch (i) {
				case 0:
					op = ALU.Operation.ADDITION;
					break;
				case 1:
					op = ALU.Operation.SUBTRACTION;
					break;
				case 2:
					op = ALU.Operation.MULTIPLICATION;
					break;
				default:
					op = ALU.Operation.DIVISION;
				}
				System.out.println("    " + op + ":");
				System.out.println("\ttotal:   \t" + detailTotal[i]);
				System.out.println("\tright:   \t" + detailRight[i]);
				System.out.println("\taccuracy:\t" + String.format("%.2f", detailRight[i] * 100.0 / detailTotal[i]) + "%");
				System.out.println();
			}
		}
		if (total == right)
			System.out.println("Congratulations!");
	}
}
