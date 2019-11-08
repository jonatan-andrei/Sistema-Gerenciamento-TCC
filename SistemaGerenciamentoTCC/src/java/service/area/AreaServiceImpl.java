package service.area;

import dao.area.AreaDAO;
import dao.area.AreaDAOImpl;
import domain.Area;
import java.util.List;

public class AreaServiceImpl implements AreaService {

    private static final AreaDAO areaDAO = new AreaDAOImpl();

    @Override
    public List<Area> listar() {
        return areaDAO.listar();
    }

}
