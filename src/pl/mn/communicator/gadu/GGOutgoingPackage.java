package pl.mn.communicator.gadu;

/**
 * Pakiet wychodz¹cy gg.
 * 
 * @author mnaglik
 */
interface GGOutgoingPackage {
	/**
	 * Zwróæ nag³ówek pakietu
	 * 
	 * @return int
	 */
	int getHeader();
	
	/**
	 * Zwróæ d³ugoœæ pakietu
	 * D³ugoœæ bez nag³ówka i inta zawieraj¹cego d³ugoœæ ca³ego pakietu.
	 *  
	 * @return int
	 */
	int getLength();
	
	/**
	 * Zwróæ bajty z zawartoœci¹ pakietu do wys³ania
	 * 
	 * @return byte[]
	 */
	byte [] getContents();
}
