SECTION = "x11/network"
DESCRIPTION = "Mail user agent"
DEPENDS = "gtk+ gpgme gnutls"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "http://sylpheed.good-day.net/sylpheed/v2.5/sylpheed-${PV}.tar.bz2 \
	"

FILES_${PN} = "${bindir} ${datadir}/pixmaps ${datadir}/applications"
FILES_${PN}-doc += "${datadir}"

EXTRA_OECONF = "--disable-ssl --enable-gnutls"

CFLAGS += "-D_GNU_SOURCE"

do_configure_prepend() {
	mkdir -p m4
	for i in $(find ${S} -name "Makefile.am") ; do
		sed -i s:'-I$(includedir)'::g $i
	done
}

inherit autotools

do_install_append() {
	install -d ${D}${datadir}/applications
	install -m 0644 sylpheed.desktop ${D}${datadir}/applications/
	install -d ${D}${datadir}/pixmaps
	install -m 0644 sylpheed.png ${D}${datadir}/pixmaps/
}


SRC_URI[md5sum] = "a6fc9e9c2a33876fddb33f25f40b820f"
SRC_URI[sha256sum] = "d18a8dc51126e25c7677dd649b7499756278e1d82b7b888d37a10fb1d8d38761"
