package pt.fredcardoso.icm.model.form;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Search implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5805303947310433665L;
	
	private String search;
	
	
	public void setSearch(String search) {
		this.search = search;
	}
	public String getSearch() {
		return search;
	}

	
	@Override
	public String toString() {
		return "Search:  " + search ;
	}
	
}
