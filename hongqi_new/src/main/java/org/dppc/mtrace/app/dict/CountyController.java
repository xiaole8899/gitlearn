package org.dppc.mtrace.app.dict;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.dict.entity.CountyEntity;
import org.dppc.mtrace.app.dict.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 地区控制层
 * @author weiyuzhen
 *
 */
@Controller
@RequestMapping("/county")
public class CountyController {
	
	@Autowired
	private CountyService countyService;
	
	@RequestMapping("/findCountyByPrId")
	public void  findCountyByPrId(CountyEntity county,HttpServletResponse response) throws IOException{
		List<CountyEntity> countyL = countyService.findCountybyPrId(county.getPrentId().split(",")[0]);
		String strc[][]=new String[countyL.size()][2];
		
		for(int i=0;i<strc.length;i++){
			for(int j=0;j<strc[i].length-1;j++){
				strc[i][j]=countyL.get(i).getcId().toString()+","+countyL.get(i).getcName();
				strc[i][j+1]=countyL.get(i).getcName();
			}
		}
		
		//将对象打印的前台
		PrintWriterUtil.writeObject(response, strc);
		
	}
	
}
