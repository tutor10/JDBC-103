import java.sql.*;

public class CallableStatement01 {

    /*
      Java'da methodlar return type sahibi olsa da olmasada method olarak adlandirilir.

      SQL'de ise data return ediyorsa "function" denir.
      Return yapmiyorsa "procudure" olarak adlandirilir.
     */

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //1.ADIM:Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2.Adim:Database'e baglan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "2807048");

        //3.Adim:Statement olustur.
        Statement st = con.createStatement();


        // CallableStatement ile function cagirmayi parametrelendirecegiz.

        //1.Adım: Function kodunu yaz:
        String sql1 ="CREATE OR REPLACE FUNCTION  toplamaF(x NUMERIC, y NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN x+y;\n" +
                "\n" +
                "END\n" +
                "$$";
        //2. Adım: Function'ı çalıştır.
        st.execute(sql1);

        //3. Adım: Function'ı çağır.
        CallableStatement cst1 = con.prepareCall("{? = call toplamaF(?, ?)}");//İlk parametre retun type

        //4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst1.registerOutParameter(1, Types.NUMERIC);
        cst1.setInt(2, 6);
        cst1.setInt(3, -6);

        //5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst1.execute();

        //6. Adım: Sonucu çağırmak için return data type tipine göre

        System.out.println(cst1.getBigDecimal(1));


//2. Örnek: Koninin hacmini hesaplayan bir function yazın.
//1.Adım: Function kodunu yaz:
        String sql2 = "CREATE OR REPLACE FUNCTION  konininHacmiF(r NUMERIC, h NUMERIC)\n" +
                "RETURNS NUMERIC\n" +
                "LANGUAGE plpgsql\n" +
                "AS\n" +
                "$$\n" +
                "BEGIN\n" +
                "\n" +
                "RETURN 3.14*r*r*h/3;\n" +
                "\n" +
                "END\n" +
                "$$";

//2. Adım: Function'ı çalıştır.
        st.execute(sql2);

//3. Adım: Fonction'ı çağır.
        CallableStatement cst2 = con.prepareCall("{? = call konininHacmiF(?, ?)}");

//4. Adım: Return için registerOurParameter() methodunu, parametreler için ise set() ... methodlarını uygula.
        cst2.registerOutParameter(1, Types.NUMERIC);
        cst2.setInt(2, 1);
        cst2.setInt(3, 6);

//5. Adım: execute() methodu ile CallableStatement'ı çalıştır.
        cst2.execute();

        //6. Adım: Sonucu çağırmak için return data type tipine göre
        System.out.printf("%.2f",cst2.getBigDecimal(1));


        System.out.println();
        String sql6="CREATE OR  REPLACE FUNCTION  concatF(a varchar(10),b varchar(10))\n" +
                "                 RETURNS varchar\n" +
                "                 LANGUAGE plpgsql\n" +
                "                 AS\n" +
                "                 $$\n" +
                "                 BEGIN \n" +
                "                 \n" +
                "                 RETURN concat(a,b); \n" +
                "                \n" +
                "                 END\n" +
                "                 $$;";

        st.execute(sql6);
        CallableStatement cst5 = con.prepareCall("{? = call concatF(?,?)}");
        cst5.registerOutParameter(1,Types.VARCHAR);
        cst5.setObject(2,"ALi");
        cst5.setObject(3,"Can");
        cst5.execute();
        System.out.println("cst5 = " + cst5.getString(1));


    }

}