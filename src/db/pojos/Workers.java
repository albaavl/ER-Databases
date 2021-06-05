package db.pojos;

import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Workers")
@XmlAccessorType(XmlAccessType.FIELD)
public class Workers {

	@XmlElement(name = "Worker")
	private List<Worker> workers = null;

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}
}
