DESCRIPTION = "Additional features for libsigc++, with a focus on multi-threading."
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "LGPL"
DEPENDS = "libsigc++-1.2"
PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/libsigcx/libsigcx-${PV}.tar.gz \
  file://libsigcx-0.6.4-add-missing-autogen-sh-swb.patch \
  file://libsigcx-0.6.4-build-swb.patch \
  file://libsigcx-0.6.4-fix-ac-try-run-swb.patch \
  file://libsigcx-200505061-fix-dist-swb.patch \
  file://libsigcx-0.6.4-pack-predeclare-swb.patch \
  file://autotool-regen.patch"
S = "${WORKDIR}/libsigcx-${PV}"

inherit autotools pkgconfig

acpaths = ""

do_stage() {
	install -d ${STAGING_LIBDIR}/sigcx-0.6/include
	install -m 0644 sigcxconfig.h ${STAGING_LIBDIR}/sigcx-0.6/include/sigcxconfig.h
	oe_libinstall -so -C sigcx libsigcx-0.6 ${STAGING_LIBDIR}

	install -d ${STAGING_INCDIR}/sigcx-0.6/sigcx
	for f in sigcx/*.h
	do
		install -m 0644 $f ${STAGING_INCDIR}/sigcx-0.6/sigcx/
	done
}

FILES_${PN}-dev += "${libdir}/sigcx-*/"


SRC_URI[md5sum] = "02e78c3cfbdb4be285d97653e563f65a"
SRC_URI[sha256sum] = "bd55717374674ed69de46c13b52aaba0420d5c68582cac34d863e44f6f1d560e"
