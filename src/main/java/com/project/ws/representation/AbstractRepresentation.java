package com.project.ws.representation;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.project.ws.domain.Link;

public abstract class AbstractRepresentation {

	@XmlElement(name="link", namespace="")
	protected List<Link> links;
	
	public List<Link> getLinks() {
		return links;
	}
	
	public void setLinks(Link...links) {
		this.links = Arrays.asList(links);
	}
}
