SECTION = "console/utils"
DESCRIPTION = "Utility to test for faulty memory subsystem"
LICENSE = "GPLv2"

SRC_URI = "http://pyropus.ca/software/memtester/old-versions/memtester-${PV}.tar.gz"
SRC_URI += "file://Makefile.patch"

SRC_URI[md5sum] = "e562451620cf5343016950462bc0dc38"
SRC_URI[sha256sum] = "ac56f0b6d6d6e58bcf2a3fa7f2c9b29894f5177871f21115a1906c535106acf6"

S = "${WORKDIR}/memtester-${PV}"

do_compile () {
	echo '${CC} ${CFLAGS} -DPOSIX -c' > conf-cc
	echo '${CC} ${LDFLAGS}' > conf-ld
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install -d ${D}${mandir}/man8
	install -m 0755 memtester ${D}${bindir}/
	install -m 0755 memtester.8 ${D}${mandir}/man8/
}

