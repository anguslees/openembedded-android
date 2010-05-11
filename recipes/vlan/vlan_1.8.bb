DESCRIPTION = "802.1q vlan support program"
RRECOMMENDS = "kernel-module-8021q"
PR = "r2"

S = "${WORKDIR}/vlan/"

SRC_URI = "http://scry.wanfear.com/~greear/vlan/vlan.1.8.tar.gz \
	   "

inherit base

CCFLAGS = "-g -D_GNU_SOURCE -Wall -I${STAGING_INCDIR}"
LDLIBS = ""

do_compile() {
	${CC} ${CCFLAGS} -c vconfig.c
	${CC} ${CCFLAGS} ${LDFLAGS} -o vconfig vconfig.o ${LDLIBS}
	${STRIP} vconfig
}

do_install() {
	install -d "${D}/usr/sbin"
	install -m 755 "${S}/vconfig" "${D}/usr/sbin/vconfig"
}


SRC_URI[md5sum] = "1edd81324b4ffc0702c9ff289a342d91"
SRC_URI[sha256sum] = "5a254457f718df733a03e9e5c39caaba9c0aac864ea69cb5d8907ec6df28e57f"
