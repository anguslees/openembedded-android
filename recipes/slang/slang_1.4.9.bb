DESCRIPTION = "slang is a library of text functions used in editors like slrn etc."
SECTION = "libs"
PRIORITY = "optional"
PR = "r1"
LICENSE = "GPL Artistic"
SRC_URI = "ftp://space.mit.edu/pub/davis/slang/v1.4/slang-${PV}.tar.bz2 \
           file://configure.patch \
           file://Makefile.patch"
inherit autotools

acpaths = "-I ${S}/autoconf"
EXTRA_OECONF="--enable-warnings"
CFLAGS_append=" -fno-strength-reduce -D_REENTRANT"
LDFLAGS_prepend="-L${S}/src/elfobjs "
EXTRA_OEMAKE="'ELF_CFLAGS=${CFLAGS} -fPIC' 'ELF_CC=${CC}' \
	      'ELF_LINK=${CC} -shared -Wl,-soname'"

do_configure_prepend () {
	mv ${S}/autoconf/aclocal.m4 ${S}/autoconf/acinclude.m4
}

do_compile () {
	oe_runmake all
	oe_runmake elf
#	oe_runmake demos
}

do_stage () {
	oe_libinstall -so -C src/elfobjs libslang ${STAGING_LIBDIR}
	oe_libinstall -a -C src/objs libslang ${STAGING_LIBDIR}
	install -m 0644 src/slang.h ${STAGING_INCDIR}
	install -m 0644 src/slcurses.h ${STAGING_INCDIR}
}

do_install () {
	oe_runmake 'DESTDIR=${D}' install install-elf
}

SRC_URI[md5sum] = "4fbb1a7f1257e065ca830deefe13d350"
SRC_URI[sha256sum] = "fa42e57e902f2161dc2b297e0dcb24ca180024770afa379027db582803ffa2fa"
