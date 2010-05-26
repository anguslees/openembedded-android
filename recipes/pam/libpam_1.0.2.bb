DESCRIPTION = "\
PAM authentication library for Linux.  \
Linux-PAM (Pluggable Authentication Modules for Linux) is a \
library that enables the local system administrator to choose \
how individual applications authenticate users. For an \
overview of the Linux-PAM library see the Linux-PAM System \
Administrators' Guide."
HOMEPAGE = "http://kernel.org/pub/linux/libs/pam"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPLv2"

DEPENDS = "flex flex-native"

# PAM is not a lot of use without configuration files and the plugins
RRECOMMENDS_${PN} = "libpam-meta libpam-base-files"

PR = "r5"

# The project is actually called Linux-PAM but that gives
# a bad OE package name because of the upper case characters
pn = "Linux-PAM"
p = "${pn}-${PV}"
S = "${WORKDIR}/${p}"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/libs/pam/library/${p}.tar.bz2 \
           file://pam-nodocs.patch "

SRC_URI_append_linux-uclibc = " file://pam-disable-nis-on-uclibc.patch"
SRC_URI_append_linux-uclibceabi = " file://pam-disable-nis-on-uclibc.patch"

inherit autotools

LEAD_SONAME = "libpam.so.*"

# maintain the pam default layout
EXTRA_OECONF += " --includedir=${includedir}/security"

EXTRA_OECONF_linux-uclibc += "--disable-nls"
EXTRA_OECONF_linux-uclibceabi += "--disable-nls"

PACKAGES_DYNAMIC += " libpam-meta pam-plugin-*"

python populate_packages_prepend () {
	import os.path

	pam_libdir = bb.data.expand('${libdir}/security', d)
	pam_libdirdebug = bb.data.expand('${libdir}/security/.debug', d)
	pam_filterdir = bb.data.expand('${libdir}/security/pam_filter', d)
	do_split_packages(d, pam_libdir, '^pam(.*)\.so$', 'pam-plugin%s', 'PAM plugin for %s', extra_depends='')
	do_split_packages(d, pam_libdir, '^pam(.*)\.la$', 'pam-plugin%s-dev', 'PAM plugin for %s dev', extra_depends='')
	if os.path.exists(pam_libdirdebug):
		do_split_packages(d, pam_libdirdebug, '^pam(.*)\.so$', 'pam-plugin%s-dbg', 'PAM plugin for %s debugging symbols', extra_depends='')
	do_split_packages(d, pam_filterdir, '^(.*)$', 'pam-filter-%s', 'PAM filter for %s', extra_depends='')

	pn = bb.data.getVar('PN', d, 1)
	metapkg =  pn + '-meta'
	bb.data.setVar('ALLOW_EMPTY_' + metapkg, "1", d)
	bb.data.setVar('FILES_' + metapkg, "", d)
	blacklist = [ pn + '-locale', pn + '-dev', pn + '-dbg', pn + '-doc' ]
	metapkg_rdepends = []
	packages = bb.data.getVar('PACKAGES', d, 1).split()
	for pkg in packages[1:]:
		if not pkg in blacklist and not pkg in metapkg_rdepends and not pkg.endswith('-dev') and not pkg.count('locale') and pkg.count('plugin'):
			metapkg_rdepends.append(pkg)
	bb.data.setVar('RDEPENDS_' + metapkg, ' '.join(metapkg_rdepends), d)
	bb.data.setVar('DESCRIPTION_' + metapkg, pn + ' meta package', d)
	packages.append(metapkg)
	bb.data.setVar('PACKAGES', ' '.join(packages), d)
}

do_stage() {
	autotools_stage_all
}

SRC_URI[md5sum] = "fc5e35645b75befae28c88b711b28ffb"
SRC_URI[sha256sum] = "42d06febf4717dd43eb557027d1b00a484c22589793438778ed69449a073a3b8"
