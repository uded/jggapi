package pl.radical.open.gg.packet.dicts;

/**
 * Created on 2004-12-15
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public interface GGPubdirConsts {

	int GG_PUBDIR50_WRITE = 0x01;
	int GG_PUBDIR50_READ = 0x02;
	int GG_PUBDIR50_SEARCH = 0x03;
	int GG_PUBDIR50_SEARCH_REPLY = 0x05;

	String UIN = "FmNumber";
	String FIRST_NAME = "firstname";
	String LAST_NAME = "lastname";
	String NICK_NAME = "nickname";
	String BIRTH_YEAR = "birthyear";
	String CITY = "city";
	String GENDER = "gender";
	String ACTIVE = "ActiveOnly";
	String FAMILY_NAME = "familyname";
	String FAMILY_CITY = "familycity";
	String START = "fmstart";
	String STATUS = "FmStatus";
	String NEXT_START = "nextstart";

}
