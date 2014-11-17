package com.gooseeker.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gooseeker.business.StationService;
import com.gooseeker.dao.PipelineDao;
import com.gooseeker.dao.StationDao;
import com.gooseeker.dao.beans.Station;

public class StationServiceImpl implements StationService
{
    private StationDao stationDao;
    private PipelineDao pipelineDao;
    
    @Autowired
    public void setPipelineDao(PipelineDao pipelineDao) {
		this.pipelineDao = pipelineDao;
	}

	@Autowired
    public void setStationDao(StationDao stationDao)
    {
        this.stationDao = stationDao;
    }

    @Override
    public Map<Long, List<Station>> queryAllStationInfo()
    {
        Map<Long, List<Station>> stationMap = new HashMap<Long, List<Station>>();
        List<Station> allStations = stationDao.getAllStations();
        for (Station station : allStations)
        {
            station.setState(0);
            List<Station> stations = stationMap.get(station.getPipelineId());
            if (null == stations)
            {
                stations = new ArrayList<Station>();
                stationMap.put(station.getPipelineId(), stations);
            }
            
            stations.add(station);
        }
        return stationMap;
    }

	@Override
	public List<Station> findStations4Page(String keyword, int start, int length) {
		return stationDao.findStations4Page(keyword, start, length);
	}

	@Override
	public int findStations4PageCount(String keyword) {
		return stationDao.findStations4PageCount(keyword);
	}

	@Override
	public int deleteStation(long id) {
		pipelineDao.updatePipelineStations(id, -1);
		return stationDao.deleteStation(id);
	}

	@Override
	public long insertStation(String name, long pipelineId, String number,String address,String subAddress, String desc) {
		long stationId = stationDao.insertStation(name, pipelineId, number, address,subAddress, desc);
		if(stationId > 0)
		{
			pipelineDao.updatePipelineStations(stationId,1);
		}
		
		return stationId;
	}

	@Override
	public Station getStation(long id) {
		return stationDao.getStationById(id);
	}

	@Override
	public int updateStation(long id, String name, String number, String pipelineId, String address, String desc) {
		return stationDao.updateStation(id, name, number, pipelineId, address, desc);
	}

	@Override
	public List<Station> listStations4Page(long pipelineId, int start, int length) {
		return stationDao.listStation4Page(pipelineId,start,length);
	}

	@Override
	public int listStations4PageCount(long pipelineId) {
		return stationDao.listStation4PageCount(pipelineId);
	}

	@Override
	public List<String> findAllAddresses() {
		return stationDao.findAllAddress();
	}

}
