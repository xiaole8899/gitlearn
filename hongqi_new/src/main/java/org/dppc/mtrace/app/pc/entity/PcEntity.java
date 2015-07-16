package org.dppc.mtrace.app.pc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;

@Entity
@Table(name="t_pc")
public class PcEntity extends BaseEntity{
	/**
	 * PC机实体类
	 */
	private static final long serialVersionUID = 1L;

	//主键
	private int pcId = 0;
	
	//PC机ip地址
	private String pcIp;
	
	//PC机mac地址
	private String mac;
	
	//PC机主板型号
	private String mainBoard;
	
	//PC机显卡型号
	private String vgaDriver;
	
	//PC机CPU
	private String cpu;
	
	//PC机内存
	private String memoryBank;
	
	//显示器
	private String monito;
	
	//键盘
	private String keyBoard;
	
	//鼠标
	private String mouse;
	
	//时间
	private Date inDate;
	
	/*//绑定时间
	private Date bindTime; 
	
	//关联电子秤
	private Set<IcEntity> ews;*/

	@Id
	@Column(name="pc_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getPcId() {
		return pcId;
	}

	public void setPcId(int pcId) {
		this.pcId = pcId;
	}

	@Column(name="pc_ip",length=50)
	public String getPcIp() {
		return pcIp;
	}

	public void setPcIp(String pcIp) {
		this.pcIp = pcIp;
	}

	@Column(name="mac",length=70)
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name="main_board",length=70)
	public String getMainBoard() {
		return mainBoard;
	}

	public void setMainBoard(String mainBoard) {
		this.mainBoard = mainBoard;
	}

	@Column(name="vga_driver",length=70)
	public String getVgaDriver() {
		return vgaDriver;
	}

	public void setVgaDriver(String vgaDriver) {
		this.vgaDriver = vgaDriver;
	}

	@Column(name="cpu",length=70)
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	@Column(name="memory_bank",length=70)
	public String getMemoryBank() {
		return memoryBank;
	}

	public void setMemoryBank(String memoryBank) {
		this.memoryBank = memoryBank;
	}

	@Column(name="monito",length=70)
	public String getMonito() {
		return monito;
	}

	public void setMonito(String monito) {
		this.monito = monito;
	}

	@Column(name="key_board",length=50)
	public String getKeyBoard() {
		return keyBoard;
	}

	public void setKeyBoard(String keyBoard) {
		this.keyBoard = keyBoard;
	}

	@Column(name="mouse",length=50)
	public String getMouse() {
		return mouse;
	}

	public void setMouse(String mouse) {
		this.mouse = mouse;
	}

	@Column(name="in_date",length=30)
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
