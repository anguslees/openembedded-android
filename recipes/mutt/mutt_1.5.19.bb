SECTION = "console/network"
DEPENDS = "ncurses gnutls gpgme"
DESCRIPTION = "Mutt is a small but very powerful text-based \
MIME mail client. It is highly configurable, and is well-suited \
to the mail power user with advanced features like key \
bindings, keyboard macros, mail threading, regular expression \
searches, and a powerful pattern matching language for selecting \
groups of messages."
LICENSE = "GPL"
PR = "r1"
SRC_URI = "ftp://ftp.mutt.org/mutt/devel/mutt-${PV}.tar.gz \
           file://patch-1.5.19.sidebar.20090522.txt;apply=yes \
	   file://529838-gnutls-autoconf.patch \
           file://makedoc.patch" 

S = "${WORKDIR}/mutt-1.5.19"

inherit autotools

EXTRA_OECONF = "--enable-gpgme --with-curses=${STAGING_LIBDIR}/.. \
	        --enable-pop --enable-imap --with-gnutls --enable-nntp"

do_compile_prepend () {
        ${BUILD_CC} doc/makedoc.c -o doc/makedoc
}


SRC_URI[md5sum] = "73b3747bc7f7c805921e8d24ebac693f"
SRC_URI[sha256sum] = "b160baedf2676a4c2c1a5c74049e2c63f00fccff43ee2594367087a08bfd63c8"
