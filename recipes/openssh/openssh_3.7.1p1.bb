DEPENDS = "zlib openssl"
SECTION = "console/network"
DESCRIPTION = "Secure rlogin/rsh/rcp/telnet replacement (OpenSSH) \
Ssh (Secure Shell) is a program for logging into a remote machine \
and for executing commands on a remote machine. \
It provides secure encrypted communications between two untrusted \
hosts over an insecure network.  X11 connections and arbitrary TCP/IP \
ports can also be forwarded over the secure channel. \
It is intended as a replacement for rlogin, rsh and rcp, and can be \
used to provide applications with a secure communication channel."
LICENSE = "openssh"
SRC_URI = "ftp://ftp.openbsd.org/pub/OpenBSD/OpenSSH/portable/openssh-${PV}.tar.gz \
           file://configure.patch \
           file://scp-nossl.patch \
           file://sshd_config"

inherit autotools

sysconfdir_append = "/ssh"
export ASKPASS_PROGRAM = "${bindir}/ssh-askpass"
export LD = "${CC}"
CFLAGS_prepend = "-I${S} "
CFLAGS_append = " -D__FILE_OFFSET_BITS=64"
LDFLAGS_prepend = "-L${S} -L${S}/openbsd-compat "
EXTRA_OECONF = "--disable-suid-ssh --with-ssl=${STAGING_LIBDIR}/ssl \
	       --with-rand-helper=no --without-pam"
EXTRA_OEMAKE = "'STRIP_OPT='"

do_configure_prepend () {
	if [ ! -e acinclude.m4 -a -e aclocal.m4 ]; then
		cp aclocal.m4 acinclude.m4
	fi
}

do_compile_append () {
	install -m 0644 ${WORKDIR}/sshd_config ${S}/
}

SRC_URI[md5sum] = "f54e574e606c08ef63ebb1ab2f7689dc"
SRC_URI[sha256sum] = "61d9b805f255e143dbbc50c6c60c24f55bc4f8f319909dd399b3a3a1ef7c77f9"
