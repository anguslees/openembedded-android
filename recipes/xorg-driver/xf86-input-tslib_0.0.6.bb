require xorg-driver-input.inc

DESCRIPTION = "X.Org X server -- tslib input driver"
RRECOMMENDS_${PN} += "tslib-calibrate"
RSUGGESTS_${PN} += "hal"
DEPENDS += "tslib"

PR = "r13"

SRC_URI = "http://www.pengutronix.de/software/xf86-input-tslib/download/xf86-input-tslib-${PV}.tar.bz2;name=archive \
           file://double-free-crash.patch \
           file://10-x11-input-tslib.fdi \
           file://xserver-174-XGetPointerControl.patch \
           file://99-xf86-input-tslib.rules \
"

do_configure_prepend() {
	rm -rf ${S}/m4/ || true
}

do_install_append() {
	install -d ${D}/${datadir}/hal/fdi/policy/20thirdparty
	install -m 0644 ${WORKDIR}/10-x11-input-tslib.fdi ${D}/${datadir}/hal/fdi/policy/20thirdparty

	install -d ${D}/lib/udev/rules.d
	install -m 0644 ${WORKDIR}/99-xf86-input-tslib.rules ${D}/lib/udev/rules.d/
}

FILES_${PN} += "${datadir}/hal /lib/udev"
SRC_URI[archive.md5sum] = "b7a4d2f11637ee3fcf432e044b1d017f"
SRC_URI[archive.sha256sum] = "5f46fdef095a6e44a69e0f0b57c7d665224b26d990d006611236d8332e85b105"
