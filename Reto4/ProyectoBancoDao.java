package Reto4;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ProyectoBancoDao {
    public List<ProyectoBancoVo> listar(String banco) throws SQLException {
        ArrayList<ProyectoBancoVo> respuesta = new ArrayList<ProyectoBancoVo>();
        Connection conn = JDBCUtilities.getConnection();
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String consulta = "SELECT P.ID_PROYECTO AS ID, P.CONSTRUCTORA, P.CIUDAD, P.CLASIFICACION,"
                + "       T.ESTRATO, L.NOMBRE||' '||L.PRIMER_APELLIDO||' '||L.SEGUNDO_APELLIDO AS LIDER"
                + " FROM PROYECTO P"
                + " JOIN TIPO T ON (P.ID_TIPO = T.ID_TIPO)"
                + " JOIN LIDER L ON (P.ID_LIDER = L.ID_LIDER)"
                + " WHERE P.BANCO_VINCULADO = ?"
                + " ORDER BY FECHA_INICIO DESC, CIUDAD, CONSTRUCTORA";
        try {
            stmt = conn.prepareStatement(consulta);
            stmt.setString(1, banco);
            rset = stmt.executeQuery();
            while (rset.next()) {
                ProyectoBancoVo vo = new ProyectoBancoVo();
                vo.setId(rset.getInt("id"));
                vo.setConstructora(rset.getString("constructora"));
                vo.setCiudad(rset.getString("ciudad"));
                vo.setClasificacion(rset.getString("clasificacion"));
                vo.setEstrato(rset.getInt("estrato"));
                vo.setLider(rset.getString("lider"));

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
