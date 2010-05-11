LICENSE = "GPL"
SECTION = "console/network"
PRIORITY = "standard"
DESCRIPTION = "World Wide Web Offline Explorer"
DEPENDS = ""

SRC_URI = "http://www.gedanken.demon.co.uk/download-wwwoffle/${PN}-${PV}.tgz \
	file://wwwoffle.init \
	file://wwwoffle.if-up \
	file://wwwoffle.if-down"

INITSCRIPT_NAME = "${PN}"

inherit autotools gettext update-rc.d

EXTRA_OEMAKE = "docdir=${datadir}/doc"

do_configure() {
	mv aclocal.m4 acinclude.m4
	autotools_do_configure
}

do_install_append() {
	install -d ${D}/${sysconfdir}/network/if-up.d
	install -m 755 ${WORKDIR}/wwwoffle.if-up ${D}/${sysconfdir}/network/if-up.d/wwwoffle
	install -d ${D}/${sysconfdir}/network/if-down.d
	install -m 755 ${WORKDIR}/wwwoffle.if-down ${D}/${sysconfdir}/network/if-down.d/wwwoffle
	install -d ${D}/${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/wwwoffle.init ${D}/${sysconfdir}/init.d/wwwoffle
}

SRC_URI[md5sum] = "a5f04c190a2f27f28cfc744c478e6aaa"
SRC_URI[sha256sum] = "14728356373c57dbafeaa8458455f1a299aee7ab395cb0de0312be5d693f7b5a"
