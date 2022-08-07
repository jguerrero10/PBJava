package Reto4;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class ComprasDeLiderDao {
    public List<ComprasDeLiderVo> listar() throws SQLException {
        ArrayList<ComprasDeLiderVo> respuesta = new ArrayList<ComprasDeLiderVo>();
        Connection conn = JDBCUtilities.getConnection();
        Statement stmt = null;
        ResultSet rset = null;
        String consulta = "SELECT L.NOMBRE ||' '||L.PRIMER_APELLIDO ||' '||L.SEGUNDO_APELLIDO AS LIDER,"
                + "       SUM(C.CANTIDAD * MC.PRECIO_UNIDAD) AS VALOR"
                + " FROM LIDER L"
                + " JOIN PROYECTO P ON (P.ID_LIDER = L.ID_LIDER)"
                + " JOIN COMPRA C ON (P.ID_PROYECTO = C.ID_PROYECTO)"
                + " JOIN MATERIALCONSTRUCCION MC ON (C.ID_MATERIALCONSTRUCCION = MC.ID_MATERIALCONSTRUCCION)"
                + " GROUP BY L.NOMBRE ||' '||L.PRIMER_APELLIDO ||' '||L.SEGUNDO_APELLIDO"
                + " ORDER BY VALOR DESC"
                + " LIMIT 10;";
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(consulta);
            while (rset.next()) {
                ComprasDeLiderVo vo = new ComprasDeLiderVo();
                vo.setLider(rset.getString("Lider"));
                vo.setValor(rset.getDouble("Valor"));

                respuesta.add(vo);
            }
        } finally {
            if (rset != null) {
                rset.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return respuesta;
    }
}
