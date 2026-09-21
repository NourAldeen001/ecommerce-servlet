package util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDBConnection {

    public static void main(String[] args) {
        System.out.println("جاري محاولة الاتصال بقاعدة البيانات...");

        try (Connection con = DbConnection.getConnection()) {

            if (con != null && !con.isClosed()) {
                System.out.println("تم الاتصال بنجاح بقاعدة البيانات: " + con.getCatalog());
            }

            String sql = "SELECT id, name, price, stock FROM products LIMIT 5";
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                boolean hasRows = false;
                System.out.println("\n--- عينة من جدول المنتجات ---");
                while (rs.next()) {
                    hasRows = true;
                    System.out.printf("ID: %d | Name: %s | Price: %.2f | Stock: %d%n",
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock"));
                }

                if (!hasRows) {
                    System.out.println("الاتصال شغال، لكن جدول products فاضي حاليًا (طبيعي لو لسه محطتش بيانات).");
                }
            }

        } catch (SQLException e) {
            System.out.println("فشل الاتصال بقاعدة البيانات!");
            System.out.println("رسالة الخطأ: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
