import java.sql.*;

public class ExecuteQuery01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //1.ADIM:Driver'a kaydol
        Class.forName("org.postgresql.Driver");

        //2.Adim:Database'e baglan
        Connection con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","2807048");

        //3.Adim:Statement olustur.
        Statement st=con.createStatement();

        //1. Örnek:  region id'si 1 olan "country name" değerlerini çağırın.

        String sql1="SELECT country_name FROM countries WHERE region_id=1";
       boolean r1=st.execute(sql1);
        System.out.println("r1 = " + r1);

        //Recordlari gormek icin ExecuteQuery() methodunu kullanmaliyiz.
        ResultSet resultSet1=st.executeQuery(sql1);

        while(resultSet1.next()){

            System.out.println(resultSet1.getString("country_name"));
        }
     //2.Örnek: "region_id"nin 2'den büyük olduğu "country_id" ve "country_name" değerlerini çağırın.

        String sql2="SELECT country_id,country_name FROM countries WHERE region_id>2";
        ResultSet resultSet2=st.executeQuery(sql2);

        System.out.println();

        while (resultSet2.next()){

            System.out.println(resultSet2.getString("country_id")+".."+resultSet2.getString("country_name"));
        }

        //3.Örnek: "number_of_employees" değeri en düşük olan satırın tüm değerlerini çağırın.

        String sql3="SELECT * FROM companies WHERE number_of_employees=(SELECT MIN( number_of_employees) FROM companies)";
        ResultSet resultSet3=st.executeQuery(sql3);

        System.out.println();

        while(resultSet3.next()){

            System.out.println(resultSet3.getInt(1)+"--"+resultSet3.getString(2)+"--"+resultSet3.getInt(3));
        }
        con.close();
        st.close();
        resultSet1.close();
        resultSet2.close();
        resultSet3.close();
    }
}

































