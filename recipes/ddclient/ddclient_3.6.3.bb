SECTION = "console/network"
DESCRIPTION = "Ddclient is a Perl client used to update dynamic DNS entries for accounts on Dynamic DNS Network Services"
HOMEPAGE = "http://ddclient.sourceforge.net/"
LICENSE = "GPL"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/ddclient/ddclient-${PV}.tar.bz2 \
	file://ip-up"

RDEPENDS_${PN} = "perl perl-module-strict perl-module-getopt-long perl-module-vars perl-module-warnings-register perl-module-warnings perl-module-carp perl-module-exporter perl-module-constant perl-module-exporter-heavy perl-module-sys-hostname perl-module-xsloader perl-module-autoloader perl-module-io-socket perl-module-io-handle perl-module-symbol perl-module-selectsaver perl-module-io perl-module-socket perl-module-errno perl-module-config perl-module-io-socket-inet perl-module-io-socket-unix perl-module-integer"

do_install() {
	install -d ${D}${sbindir} ${D}${sysconfdir} ${D}${sysconfdir}/init.d
	install -d ${D}${sysconfdir}/ppp/ip-up.d/ install -d ${D}${docdir}/ddclient
	install -m 755 ${S}/ddclient ${D}${sbindir}
	install ${S}/sample-etc_ddclient.conf ${D}${sysconfdir}/ddclient.conf
	install -m 755 ${WORKDIR}/ip-up ${D}${sysconfdir}/ppp/ip-up.d/ddclient
	install -m 755 ${S}/sample-etc_rc.d_init.d_ddclient ${D}${sysconfdir}/init.d/ddclient
	install ${S}/README* ${D}${docdir}/ddclient
	install ${S}/COPY* ${D}${docdir}/ddclient
	install ${S}/sample* ${D}${docdir}/ddclient
}

CONFFILES_${PN}_nylon = "${sysconfdir}/ddclient.conf"

SRC_URI[md5sum] = "42a3a420e492c0111adc58ac08ff6d04"
SRC_URI[sha256sum] = "28be7102bde3bc9b39be2732b89da9fbab79b7ac3898075ed414a45258bb3026"
