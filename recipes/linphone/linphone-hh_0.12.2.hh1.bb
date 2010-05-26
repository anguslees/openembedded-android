LICENSE = "GPL"
SECTION = "x11/utils"
PR = "r4"

DESCRIPTION = "SIP-based IP phone (GPE edition)"
DEPENDS = "libosip gtk+ libogg alsa-lib"
SRC_URI = "http://handhelds.org/packages/linphone/linphone-${PV}.tar.gz \
	file://osipua-ipv6-lossage.patch \
	file://gpe-cross-lossage.patch \
	file://disable-gtk-doc.patch \
	file://dotdesktop.patch \
	file://segfault.patch"

S = "${WORKDIR}/linphone-${PV}"

FILES_${PN} += "${datadir}/linphonec"

inherit autotools

EXTRA_OECONF = "--disable-gnome_ui --disable-gtk-doc"

do_configure() {
	# ffmpeg is in AC_SUBDIRS, but doesn't actually use autoconf.
	# Autoreconf will try to recurse into there and blow up.
	for dir in . speex oRTP osipua; do
		( cd $dir; libtoolize --force; aclocal; automake; autoconf )
	done

	oe_runconf
}

do_install_append() {
	mv ${D}${datadir}/gnome/apps/Internet ${D}${datadir}/applications
	rm -f ${D}${datadir}/sounds/linphone/hello*.wav
	rm -f ${D}${datadir}/sounds/linphone/rings/oldphone.wav
}

SRC_URI[md5sum] = "b1e3bdcd92f57aa5f3e68cd84ab330eb"
SRC_URI[sha256sum] = "460d29f603864281bef60a919e79d84d5477ac14e930da446e765d17f2706a6c"
