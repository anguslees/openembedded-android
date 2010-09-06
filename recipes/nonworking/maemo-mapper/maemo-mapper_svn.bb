DESCRIPTION = "GPS navigation/map display software"
LICENSE = "GPL"
DEPENDS = "gdbm libconic sqlite3 gtk+ libhildonfm libosso osso-ic-oss libosso-help gnome-vfs dbus bluez-libs"
RDEPENDS_${PN} = "bluez-utils"
RRECOMMENDS_${PN} = "gpsd flite"
SRCREV = "118"
PV = "2.0.3+svnr${SRCPV}"
PR = "r3"

SRC_URI = "svn://garage.maemo.org/svn/maemo-mapper;proto=https;module=trunk \
           file://mapper-hildon1.diff;maxrev=108" 

S = "${WORKDIR}/trunk"

inherit autotools pkgconfig

do_install_append () {
	install -d ${D}${datadir}/applications/
	mv ${D}/maemo-mapper.desktop ${D}${datadir}/applications/
}

#FILES_${PN} += "${datadir}/icons"
