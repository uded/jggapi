package pl.radical.open.gg;

import lombok.Data;

/**
 * This is an abstract class that is common for a query to Gadu-Gadu's public directory and reply from it.
 * <p>
 * Created on 2004-12-17
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@Data
public class PublicDirSearch {

	private Integer uin;
	private String firstName;
	private String nickName;
	private String birthYear;
	private String city;
	private String familyName;
	private String familyCity;

}
