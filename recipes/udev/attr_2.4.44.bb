DESCRIPTION = "Commands for Manipulating Filesystem Extended Attributes"
LICENSE = "GPLv2"

PR = "r3"

SRC_URI = "http://mirror.its.uidaho.edu/pub/savannah/attr/attr-${PV}.src.tar.gz"

inherit gettext autotools lib_package

EXTRA_OECONF = " --enable-gettext=yes \
                 ac_cv_path_XGETTEXT=${STAGING_BINDIR_NATIVE}/xgettext \
                 ac_cv_path_MSGFMT=${STAGING_BINDIR_NATIVE}/msgfmt \
                 ac_cv_path_MSGMERGE=${STAGING_BINDIR_NATIVE}/msgmerge "

LDFLAGS_append_libc-uclibc += " -lintl"

TOPDIR[unexport] = "1"

do_configure_append() {
	# gettext hack
	echo "#define _(str) str" >> ${S}/include/config.h
}

do_install() {
	export PKG_BIN_DIR=${D}${bindir}
	export PKG_SBIN_DIR=${D}${sbindir}
	export PKG_LIB_DIR=${D}${libdir}
	export PKG_DEVLIB_DIR=${D}${libexecdir}
	export PKG_INC_DIR=${D}${includedir}/attr
	export PKG_MAN_DIR=${D}${mandir}
	export PKG_DOC_DIR=${D}${datadir}/doc/attr
	export PKG_LOCALE_DIR=${D}${datadir}/locale

	oe_runmake -e install install-dev install-lib

	sed -i -e s:installed=yes:installed=no: -e s:${STAGING_LIBDIR}:${libdir}:g ${D}${libdir}/libattr.la

	# Move .a and .la into libdir and remove symlinks pointing to ${S}
	for file in ${D}${libexecdir}/*a ; do
		rm ${D}${libdir}/$(basename $file)
		mv $file ${D}${libdir}
	done
	rm ${D}${libexecdir} -rf
}

SRC_URI[md5sum] = "d132c119831c27350e10b9f885711adc"
SRC_URI[sha256sum] = "9f6214b8e53f4bba651ac5a72c0f6193b12aa21fbf1d675d89a7b4bc45264498"
