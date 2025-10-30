/**
 * 
 */
package mouse.cmn;

/**
 * 목록 검색용 VO
 * 
 */
public class SearchVO extends DTO{
	private String searchDiv; 	//검색구분
	private String searchWord;	//검색어
	
	public SearchVO() {
		super();
	}
	
	public SearchVO(String searchDiv, String searchWord) {
		super();
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}

	public String getSearchDiv() {
		return searchDiv;
	}
	
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	
	public String getSearchWord() {
		return searchWord;
	}
	
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	@Override
	public String toString() {
		return "SearchVO [searchDiv=" + searchDiv + ", searchWord=" + searchWord + ", toString()=" + super.toString()
				+ "]";
	}

	
	
	
	
	


	
	
}
