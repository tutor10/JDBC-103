import java.sql.*;

public class ExecuteUpdate01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //1.ADIM:Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2.Adim:Database'e baglan
        Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","2807048");

        //3.Adim:Statement olustur.
        Statement st=con.createStatement();

        //1. Örnek: number_of_employees değeri ortalama çalışan sayısından az olan number_of_employees değerlerini 16000 olarak UPDATE edin.

        String sql1 = "UPDATE companies\n" +
                "SET number_of_employees = 16000\n" +
                "WHERE number_of_employees < (SELECT AVG(number_of_employees)\n" +
                "                             FROM companies)";

        int updateEdilenSatirSayisi = st.executeUpdate(sql1);
        System.out.println("updateEdilenSatirSayisi = " + updateEdilenSatirSayisi);

        ResultSet resultSet1 = st.executeQuery("SELECT * FROM companies");

        while (resultSet1.next()) {

            System.out.println(resultSet1.getInt(1) + "--" + resultSet1.getString(2) + "--" + resultSet1.getInt(3));

        }

        con.close();
        st.close();
        resultSet1.close();

    }

}