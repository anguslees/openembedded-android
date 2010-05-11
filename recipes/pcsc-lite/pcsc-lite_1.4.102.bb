DESCRIPTION = "PC/SC Lite smart card framework and applications"
HOMEPAGE = "http://pcsclite.alioth.debian.org/"
LICENSE = "BSD"
PR = "r0"

DEPENDS = "hal"
RDEPENDS_${PN} = "hal"

SRC_URI = "http://alioth.debian.org/download.php/2479/pcsc-lite-${PV}.tar.bz2 \
           file://pcscd.init "

inherit autotools update-rc.d

INITSCRIPT_NAME = "pcscd"
INITSCRIPT_PARAMS = "defaults"

EXTRA_OECONF = " \
	--enable-libhal \
	--disable-libusb \
	--enable-usbdropdir=${libdir}/pcsc/drivers \
	"

do_stage() {
	autotools_stage_all
}

do_install() {
	oe_runmake DESTDIR="${D}" install
	install -d "${D}/etc/init.d"
	install -m 755 "${WORKDIR}/pcscd.init" "${D}/etc/init.d/pcscd"
}

PACKAGES =+ "libpcsclite"

FILES_libpcsclite = "${libdir}/libpcsclite.so.*"

SRC_URI[md5sum] = "b97d3a725ab93662adf09acfbc49efeb"
SRC_URI[sha256sum] = "56fc9e6324539537324b071e5b51c3388df9bfa92fb7e210b989b463122fc333"
