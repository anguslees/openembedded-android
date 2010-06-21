DESCRIPTION = "AbiWord is a free word processing program similar to Microsoft(r) Word"
HOMEPAGE = "http://www.abiword.org"
SECTION = "x11/office"
LICENSE = "GPLv2"
DEPENDS = "asio boost loudmouth libwpd librsvg goffice poppler libglade"
RDEPENDS_${PN} = "abiword"
PR = "r1"


SRC_URI = "http://www.abiword.org/downloads/abiword/${PV}/source/abiword-plugins-${PV}.tar.gz;name=plugins \
           http://www.abiword.org/downloads/abiword/${PV}/source/abiword-${PV}.tar.gz;name=archive \
#           file://abiword-cxx-for-ld-fix.patch \
	   "

DEFAULT_PREFERENCE = "2"

inherit autotools

PARALLEL_MAKE=""

#export LDFLAGS += " -lstdc++ "

EXTRA_OECONF = " --enable-shared=yes \
                 --enable-static \
		 --without-libwmf \
                 --without-inter7eps \
		 --with-abiword=${WORKDIR}/abiword-${PV} \
		 --with-boost=${STAGING_DIR_HOST} \ 
		 --with-boost-thread=boost_thread-mt \
               "

PACKAGES_DYNAMIC = "abiword-plugin-*"

python populate_packages_prepend () {
	abiword_libdir    = bb.data.expand('${libdir}/abiword-2.6/plugins', d)
	do_split_packages(d, abiword_libdir, '^libAbi(.*)\.so$', 'abiword-plugin-%s', 'Abiword plugin for %s', extra_depends='')
        do_split_packages(d, abiword_libdir, '^libAbi(.*)\.la$', 'abiword-plugin-%s-dev', 'Abiword plugin for %s', extra_depends='')

	metapkg = "abiword-plugins"
	bb.data.setVar('ALLOW_EMPTY_' + metapkg, "1", d)
	bb.data.setVar('FILES_' + metapkg, "", d)
	blacklist = [ 'abiword-plugins-dbg', 'abiword-plugins', 'abiword-plugins-doc', 'abiword-plugins-dev', 'abiword-plugins-locale' ]
	metapkg_rdepends = []
	packages = bb.data.getVar('PACKAGES', d, 1).split()
	for pkg in packages[1:]:
		if not pkg in blacklist and not pkg in metapkg_rdepends:
			print "Modifying ", pkg
			metapkg_rdepends.append(pkg)
	bb.data.setVar('RDEPENDS_' + metapkg, ' '.join(metapkg_rdepends), d)
	bb.data.setVar('DESCRIPTION_' + metapkg, 'abiword-plugin meta package', d)
	packages.append(metapkg)
	bb.data.setVar('PACKAGES', ' '.join(packages), d)
}


PACKAGES =+ "abiword-plugin-collab-glade"

FILES_abiword-plugin-collab-glade += "${datadir}"
RDEPENDS_abiword-plugin-collab-glade = "abiword-plugin-collab"

FILES_${PN}-dbg += "${libdir}/abiword-2.6/plugins/.debug"


SRC_URI[plugins.md5sum] = "421c49723e209c971ddb0798c1b313a9"
SRC_URI[plugins.sha256sum] = "151517ae94e0f4c26370f31c903078bd0561b8088a63b532408ca128f0e65448"
SRC_URI[archive.md5sum] = "fab04d8ef999c303f720197adf261310"
SRC_URI[archive.sha256sum] = "b6656a0da13d94b334f02637c89d8fe13aa54752040ad1b8f14f668d8cb96e93"
