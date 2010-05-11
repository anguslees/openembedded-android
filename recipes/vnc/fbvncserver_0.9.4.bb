DESCRIPTION = "Framebuffer VNC server"
SECTION = "console/utils"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "libvncserver jpeg zlib"
RDEPENDS = "fbvncserver-kmodule libvncserver-storepasswd libvncserver-javaapplet"
PR = "r3"

SRC_URI = "http://sdgsystems.com/download/fbvncserver-${PV}.tar.gz \
           file://libvncs0.6.patch;patch=1 \
           file://paths.patch;patch=1 \
           file://kernelinclude.patch;patch=1 \
	   file://buildfix.patch;patch=1 \
	   file://ipaq.patch;patch=1 \
           file://init"

S = "${WORKDIR}/fbvncserver-${PV}"

export INCLUDES = "-I${STAGING_INCDIR}"
export LIBS = "-L${STAGING_LIBDIR} -lpthread"
export VNCSERVER_DIR = "${STAGING_LIBDIR}"
export ZAURUS_ZLIB_LIBS = "${STAGING_LIBDIR}"
export ZAURUS_JPEG_LIBS = "${STAGING_LIBDIR}"

inherit update-rc.d

INITSCRIPT_NAME = "fbvncinput"
INITSCRIPT_PARAMS = "defaults 97"

FBVNCSERVER_SYSTEM = "zaurus"
FBVNCSERVER_SYSTEM_h3600 = "ipaq"
FBVNCSERVER_SYSTEM_h3900 = "ipaq"

do_compile () {
	oe_runmake ${FBVNCSERVER_SYSTEM}_fbvncserver ${FBVNCSERVER_SYSTEM}_tssimd
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${FBVNCSERVER_SYSTEM}_fbvncserver ${D}${bindir}/fbvncserver
	install -m 0755 ${FBVNCSERVER_SYSTEM}_tssimd ${D}${bindir}/tssimd

	install -d ${D}${datadir}/fbvncserver
	install -m 0644 ${FBVNCSERVER_SYSTEM}_panel.jpg ${D}${datadir}/fbvncserver/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/fbvncinput
}

FILES_${PN} += " /usr/share/fbvncserver/*.jpg"


SRC_URI[md5sum] = "01a37f17857641253541307f59dd8cc5"
SRC_URI[sha256sum] = "e22ed10ecba059904649f344be260512fdb3cbecbd95ca965f8b5cc3ea1785b5"
