package pl.radical.open.gg.packet.dicts;

import lombok.Getter;

/**
 * Created on 2004-12-12
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public enum GGVersion {
	VERSION_80_build_8283(0x2e),
	VERSION_80_build_4881(0x2d),
	VERSION_77_build_3315(0x2a),
	VERSION_76_build_1688(0x29),
	VERSION_7_5_0_build_2201(0x28),
	VERSION_70_build_22(0x27),
	VERSION_70_build_20(0x26),
	VERSION_70_build_1(0x25),
	VERSION_60_1_build_155(0x24), // 7.6 (1359)
	VERSION_60_1_build_140(0x22),
	VERSION_60_1_build_133(0x21),
	VERSION_60(0x20),
	VERSION_5_7_beta_build_121(0x1E),
	VERSION_5_6_beta(0x1C),
	VERSION_5_0_5(0x1B),
	VERSION_5_0_3(0x19),
	VERSION_5_0_0(0x18), // 4.9.3, 5.0.0, 5.0.1
	VERSION_4_9_2(0x17),
	VERSION_4_9_1(0x16),
	VERSION_4_8_9(0x15),
	VERSION_4_8_3(0x14), // 4.8.3, 4.8.1
	VERSION_4_6_10(0x11), // 4.6.10, 4.6.1
	VERSION_4_5_22(0x10), // 4.5.22, 4.5.21, 4.5.19, 4.5.17, 4.5.15
	VERSION_4_5_12(0x0F), // 4.5.12
	VERSION_4_0_30(0x0B); // 4.0.30, 4.0.29, 4.0.28, 4.0.25

	@Getter
	private int code;

	@Getter
	private String description;

	private GGVersion(final int code) {
		this.code = code;
	}

	private GGVersion(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

}
