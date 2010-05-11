SECTION = "x11/base"
DESCRIPTION = "Clearlooks theme engine for GTK"
LICENSE = "GPLv2"
DEPENDS = "gtk+"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/clearlooks/clearlooks-0.6.2.tar.bz2"

S = "${WORKDIR}/clearlooks-${PV}"

PACKAGES += "gtk-theme-clearlooks"
FILES_${PN} = "${libdir}/gtk-2.0/*/engines/*.so"
FILES_${PN}-dev = "${libdir}/gtk-2.0/*/engines/*"
FILES_${PN}-dbg += "${libdir}/gtk-2.0/*/engines/.debug/*"
FILES_gtk-theme-clearlooks = "${datadir}/icons ${datadir}/themes"

inherit autotools

do_configure_prepend() {
	for i in `ls gtk-common`; do
		ln -sf ../gtk-common/$i gtk2-engine/$i
	done
}


SRC_URI[md5sum] = "451ef33d1bffa261c5cbe01182199f97"
SRC_URI[sha256sum] = "be080113c9e9d137ee14eaf7f731c5ae58d24924748aaa725d0f061d59265f3b"
