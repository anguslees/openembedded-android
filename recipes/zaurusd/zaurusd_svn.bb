DESCRIPTION = "Daemon to handle device specific features."
SECTION = "base"
LICENSE = "GPL"
DEPENDS = "tslib"
RDEPENDS = "procps"
SRCDATE = "20090501"
PV = "0.0+svn${SRCDATE}"
PR = "r22"

SRC_URI = "svn://svn.o-hand.com/repos/misc/trunk;module=zaurusd;proto=http \
           file://zaurus-hinge.in \
	   file://add-poodle.patch \
	   file://alsa-cxx00-default.state.patch \
	   file://alsa-6000x-default.state.patch \
	   file://disable-alsa-handling.patch \
	   file://avoid-rotated-server.patch \
	   file://zaurus-hinge.matchbox-portrait \
	   file://zaurus-hinge.matchbox-landscape \
	   file://zaurus-hinge.bl-on \
	   file://zaurus-hinge.bl-off \
	   file://01-check-toggle-landscape \
	   file://01-check-toggle-portait \
       file://use-ts-symlink-instead-of-hardcoding.diff "


S = "${WORKDIR}/${PN}"

PACKAGE_ARCH = "${MACHINE_ARCH}"

do_configure_prepend () {
	cp ${WORKDIR}/zaurus-hinge.in ${S}/scripts
}

do_install_append() {
	install -d "${D}/etc/zaurusd/hinge-landscape.d"
	install -d "${D}/etc/zaurusd/hinge-portrait.d"
	install -d "${D}/etc/zaurusd/hinge-close.d"
	install -d ${D}/etc/apm/resume.d

	install -m 0755 "${WORKDIR}/zaurus-hinge.bl-on" "${D}/etc/apm/resume.d/00-backlight-on"
	install -m 0755 "${WORKDIR}/zaurus-hinge.bl-on" "${D}/etc/zaurusd/hinge-landscape.d/00-backlight-on"
	install -m 0755 "${WORKDIR}/zaurus-hinge.bl-on" "${D}/etc/zaurusd/hinge-portrait.d/00-backlight-on"
	install -m 0755 "${WORKDIR}/zaurus-hinge.bl-off" "${D}/etc/zaurusd/hinge-close.d/00-backlight-off"

	install -m 0755 "${WORKDIR}/01-check-toggle-landscape" "${D}/etc/zaurusd/hinge-landscape.d/01-check-toggle"
	install -m 0755 "${WORKDIR}/01-check-toggle-portait" "${D}/etc/zaurusd/hinge-portrait.d/01-check-toggle"

	install -m 0755 "${WORKDIR}/zaurus-hinge.matchbox-landscape" "${D}/etc/zaurusd/hinge-landscape.d/20-matchbox-landscape"
	install -m 0755 "${WORKDIR}/zaurus-hinge.matchbox-portrait" "${D}/etc/zaurusd/hinge-portrait.d/20-matchbox-portrait"
}

inherit autotools pkgconfig update-rc.d

INITSCRIPT_NAME = "zaurusd"
INITSCRIPT_PARAMS = "start 99 5 2 . stop 20 0 1 6 ."
