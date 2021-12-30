import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.lang.Math;

public class Part1 {

    public static int add_x[] = {12, 13, 12, -13, 11, 15, -14, 12, -8, 14, -9, -11, -6, -5};
    public static int add_y[] = {1, 9, 11, 6, 6, 13, 13, 5, 7, 2, 10, 14, 7, 1};
    public static boolean div_by_1[] = {true, true, true, false, true, true, false, true, false, true, false, false, false, false, };
    //div 1: 1,2,3,5,6,8,10
    //div 26: 4,7,9,11,12,13,14

    public static void main(String[] args) {
        ArrayList<Instruction[]> instruction_list = new ArrayList<Instruction[]>();
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line = br.readLine();
            int i = 0;
            Instruction instruction_array[] = new Instruction[17];
            while ((line = br.readLine()) != null) {
                if (line.contains("inp")) {
                    instruction_list.add(instruction_array.clone());
                    instruction_array = new Instruction[17];
                    i = 0;
                } else {
                    instruction_array[i] = getInstruction(line);
                    i++;
                }
            }
            instruction_list.add(instruction_array);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        buildFormulaTest(instruction_list);
    }

    public static getInverseValue(Instruction instr, max ) {
        switch (instr.op) {
            case ADD:

            case MUL:
            case DIV:
            case MOD:
            case EQL:
        }
    }

    public static long solveEquation(long max_z, int step) {
        long x, z=0;
        for (int i = 1; i <= 9; i++) {
            while (true) {
                x = z%26+add_x[step];
                if(!div_by_1[step])
                    z = Math.floorDiv(z, 26);
                if (z>max_z)
                    break;
                if
                    //TODO: check if w==x and change controlflow accordingly

                z++;
            }
        }
    }

    public static long equationCase2(int w) {
        while()
    }

    public static void buildFormulaTest(ArrayList<Instruction[]> instruction_list) {
        Instruction instr_array[] = instruction_list.get(13);
        String formula = "z";
        for (int i = instr_array.length-1; i >= 0; i--) {
            Instruction instr = instr_array[i];
            switch(instr.op) {
                case ADD:
                    if (instr.concrete_op)
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "+" + instr.concrete + ")");
                    else
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "+" + instr.reg2 + ")");
                    break;
                case MUL:
                    if (instr.concrete_op)
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "*" + instr.concrete + ")");
                    else
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "*" + instr.reg2 + ")");
                    break;
                case DIV:
                    if (instr.concrete_op)
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "/" + instr.concrete + ")");
                    else
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "/" + instr.reg2 + ")");
                    break;
                case MOD:
                    if (instr.concrete_op)
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "%" + instr.concrete + ")");
                    else
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "%" + instr.reg2 + ")");
                    break;
                case EQL:
                    if (instr.concrete_op)
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "==" + instr.concrete + ")");
                    else
                        formula = formula.replaceAll("" + instr.reg1, "(" + instr.reg1  + "==" + instr.reg2 + ")");
                    break;
                default: break;
            }
        }
        System.out.println(formula);
    }

    public static Instruction getInstruction(String line) {
        Operation op;
        if (line.contains("add"))
            op = Operation.ADD;
        else if (line.contains("mul"))
            op = Operation.MUL;
        else if (line.contains("div"))
            op = Operation.DIV;
        else if (line.contains("mod"))
            op = Operation.MOD;
        else
            op = Operation.EQL;

        String helper = line.replaceAll("[^0-9]", "");
        if (!helper.equals(""))
            return new Instruction(line.charAt(4), Integer.parseInt(helper), op);

        return new Instruction(line.charAt(4), line.charAt(6), op);
    }


    public static class Instruction {
        char reg1;
        char reg2;
        int concrete;
        boolean concrete_op;
        Operation op;

        public Instruction(char reg1, char reg2, Operation op) {
            this.reg1 = reg1;
            this.reg2 = reg2;
            this.op = op;
            this.concrete_op = false;
        }

        public Instruction(char reg1, int concrete, Operation op) {
            this.reg1 = reg1;
            this.concrete = concrete;
            this.op = op;
            this.concrete_op = true;
        }
    }
}