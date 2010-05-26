# PAM authentication library for Linux - Linux-PAM
#
# NOTE: this is a library with plug-in modules, at present all
# the modules are built and installed into the main libpam
# ipkg.  This causes lots of problems (e.g. it is not possible
# to build on uClibC) so *do not* rely on this behaviour -
# assume the modules will be moved to individual ipks (like
# the kernel modules.)
#
DESCRIPTION = "\
PAM authentication library for Linux.  \
Linux-PAM (Pluggable Authentication Modules for Linux) is a \
library that enables the local system administrator to choose \
how individual applications authenticate users. For an \
overview of the Linux-PAM library see the Linux-PAM System \
Administrators' Guide."
HOMEPAGE = "http://www.kernel.org/pub/linux/libs/pam"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2"
PR = "r2"

# The project is actually called Linux-PAM but that gives
# a bad OE package name because of the upper case characters
pn = "Linux-PAM"
p = "${pn}-${PV}"
S = "${WORKDIR}/${p}"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/libs/pam/pre/library/${p}.tar.bz2"

# the patches are necessary to get the autoreconf and cross build
# to work correctly
SRC_URI += " file://libpam-config.patch"
# The Makefile uses 'FAKEROOT' not DESTDIR.
SRC_URI += " file://libpam-make.patch"

inherit autotools

# maintain the pam default layout
EXTRA_OECONF += " --includedir=${includedir}/security"

# EXTRA_OECONF += " --enable-static-libpam"
# Disable building of the documentation - it requires too many different
# programs installed on the build system and is a waste of time.  This
# leaves the man documentation in the build.
EXTRA_OECONF += "ac_cv_prog_HAVE_SGML2TXT=no"
EXTRA_OECONF += "ac_cv_prog_HAVE_SGML2HTML=no"
EXTRA_OECONF += "ac_cv_prog_HAVE_SGML2LATEX=no"
EXTRA_OECONF += "ac_cv_prog_HAVE_PS2PDF=no"
EXTRA_OECONF += "ac_cv_prog_HAVE_SGML2PS=no"

LEAD_SONAME = "libpam.so.*"

# This is crude - the modules maybe should each have independent ipks
FILES_${PN} += "/usr/lib/security/pam_*.so /usr/lib/security/pam_filter/*"

do_stage() {
	autotools_stage_all
}

# An attempt to build on uclibc will fail, causing annoyance,
# so force the package to be skipped here (this will cause a
# 'nothing provides' error)
#NOTE: this can be fixed, but it means hacking the modules so
# that those which use YP don't get built on uClibC, this looks
# like a big patch...
python () {
    os = bb.data.getVar("TARGET_OS", d, 1)
    if os == "linux-uclibc":
        raise bb.parse.SkipPackage("Some PAM modules require rpcsvc/yp.h, uClibC does not provide this")
}

SRC_URI[md5sum] = "0b89b73970c7d490ad9a13718b74d27b"
SRC_URI[sha256sum] = "5ed480cb76c91c2739ddba87f15437510e58c60dfdd7ead6f469149b2da94bb7"
