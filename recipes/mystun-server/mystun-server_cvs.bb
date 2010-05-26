DESCRIPTION = "STUN server used to determine IP behind NAT firewall"
HOMEPAGE = "http://developer.berlios.de/projects/mystun/"
SECTION = "console/telephony"
PRIORITY = "optional"
LICENSE = "GPL"
SRCDATE = "20060814"
PV = "1:0.0+cvs${SRCDATE}"

SRC_URI="cvs://anonymous@cvs.mystun.berlios.de/cvsroot/mystun;module=mystun \
	file://init \
	file://nossl.patch"

S = "${WORKDIR}/mystun"

do_compile() {
	oe_runmake LIBS=-lpthread CFLAGS= server
}

do_install() {
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}/${sysconfdir}/init.d/mystun-server
	install -d ${D}${sbindir}
	install -m 0755 ${S}/server.exe ${D}${sbindir}/mystun-server
}
