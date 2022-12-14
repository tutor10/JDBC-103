import java.sql.*;


public class ExecuteQuery02 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.ADIM:Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2.Adim:Database'e baglan
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "2807048");

        //3.Adim:Statement olustur.
        Statement st = con.createStatement();

        //1. Örnek: companies tablosundan en yüksek ikinci number_of_employees değeri olan company ve
        // number_of_employees değerlerini çağırın.

        //1.yol:OFFSET ve FETCH next kullanarak
        String sql1 = "SELECT company,number_of_employees FROM companies ORDER BY number_of_employees DESC OFFSET 1 LIMIT 1";//VEY AOFFSET 1   ROW FETCH NEXT 1 ROW ONLY
        ResultSet resultSet1 = st.executeQuery(sql1);                                                                       // YAZILABILIR

        while(resultSet1.next()){

            System.out.println(resultSet1.getString("company") + "---" + resultSet1.getInt("number_of_employees"));
        }

        //2.yol:Subquery kullanarak

        String sql2="\n" +
                "SELECT company, number_of_employees\n" +
                "FROM companies\n" +
                "WHERE number_of_employees = (SELECT MAX(number_of_employees)\n" +
                "                            FROM companies\n" +
                "                            WHERE number_of_employees < (SELECT MAX(number_of_employees)\n" +
                "                                                         FROM companies))";

        ResultSet resultSet2= st.executeQuery(sql2);

        while(resultSet2.next()){

            System.out.println(resultSet2.getString("company")+"---"+ resultSet2.getInt("number_of_employees"));
        }
        con.close();
        st.close();
        resultSet1.close();
        resultSet2.close();
    }
}