SECTION = "libs"
DESCRIPTION = "an ELF object file access library \
The elf library provides routines to access, and manipulate, Elf object \
files. It is still not complete, but is required for a number of programs, \
such as Eli (a state of the art compiler generation system), and Elk (the \
Extension Language Kit - an implementation of the Scheme programming \
language.)"
LICENSE = "LGPL"

SRC_URI = "http://www.mr511.de/software/libelf-${PV}.tar.gz"

inherit autotools

do_configure_prepend () {
	if test ! -e acinclude.m4; then
		cp aclocal.m4 acinclude.m4
	fi
}

do_install () {
	oe_runmake 'prefix=${D}${prefix}' 'exec_prefix=${D}${exec_prefix}' \
		   'libdir=${D}${libdir}' 'includedir=${D}${includedir}' \
		   install
}

do_stage () {
	oe_libinstall -so -C lib libelf ${STAGING_LIBDIR}/
	install -d ${STAGING_INCDIR}/libelf
	for i in libelf.h nlist.h gelf.h sys_elf.h; do
		install -m 0644 lib/$i ${STAGING_INCDIR}/libelf/
	done
	make includedir=${STAGING_INCDIR} install-compat
}

SRC_URI[md5sum] = "f85d6dc4b4ef1c126080d86cf0ce63fd"
SRC_URI[sha256sum] = "644262514d0f6c3572f66935921a5290383a15e804e9b085e293ab78102d3513"
