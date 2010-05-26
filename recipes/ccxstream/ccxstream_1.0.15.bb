DESCRIPTION = "XStream Server"
DEPENDS = "readline"
LICENSE = "GPLv2"
PR = "r2"

SRC_URI = "http://surfnet.dl.sourceforge.net/sourceforge/xbplayer/${P}.tar.gz \
	file://ccxstream-termcap.patch \
	file://ccxstream.init \
	file://ccxstream.conf"

inherit autotools

do_install() {
	# add startup and sample config
	install -d ${D}${sysconfdir}/init.d
	install -d ${D}${sbindir}
	install -m 0755 ${WORKDIR}/ccxstream.init ${D}${sysconfdir}/init.d/ccxstream
	install -m 0644 ${WORKDIR}/ccxstream.conf ${D}${sysconfdir}/ccxstream.conf
	install -m 0755 ccxstream ${D}${sbindir}/ccxstream || exit 1
}

SRC_URI[md5sum] = "c589fff48ce541f26b394c9d82ccbead"
SRC_URI[sha256sum] = "2dec31ba4a176c921a653c0949a53c2d3497e047737724350897fd2534db18fe"
