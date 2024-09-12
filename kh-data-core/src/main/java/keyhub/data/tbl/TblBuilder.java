/*
 * MIT License
 *
 * Copyright (c) 2024 KH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package keyhub.data.tbl;

import keyhub.data.row.Row;
import keyhub.data.schema.Schema;

import java.util.List;
import java.util.Scanner;

public interface TblBuilder {
    static TblBuilder forRowSet(Schema schema) {
        return TblBuilderImplement.forRowSet(schema);
    }
    TblBuilder addRawRow(List<Object> row);
    TblBuilder addRawRows(List<List<Object>> rows);
    TblBuilder addRow(Row row);
    TblBuilder addRows(List<Row> rows);
    Tbl build();

    public class Main {
        public static void main(String[] args){
            Scanner in=new Scanner(System.in);
            int n = in.nextInt();
            String[] str = new String[n];
            for(int i=0; i<n; i++) {
                str[i] = in.nextLine();
            }
            Main m = new Main();
            for(String s : m.solution(str)){
                System.out.println(s);
            }
        }

        public String[] solution(String[] str){
            String[] result = new String[str.length+1];
            for(int i=0; i<str.length; i++){
                char[] arr = str[i].toCharArray();
                StringBuilder sb = new StringBuilder();
                for(int j=arr.length-1; j >= 0; j--){
                    sb.append(arr[j]);
                }
                result[i] = sb.toString();
            }
            return result;
        }
    }
}
