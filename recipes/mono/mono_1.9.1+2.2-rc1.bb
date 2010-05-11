require mono_2.2.0.inc

DEPENDS = "mono-native mono-mcs-intermediate glib-2.0 perl-native"

DEFAULT_PREFERENCE = "-1"

PR = "r3"

# mono makes use of non-thumb-compatible inline asm.
ARM_INSTRUCTION_SET = "arm"

SRC_URI += "file://configure.patch;patch=1 \
            file://disable-docs.patch;patch=1 \
            file://disable-monoburg.patch;patch=1 \
            file://fix-eabi-syscall.patch;patch=1 \
	   "

# Per http://www.mono-project.com/Mono:ARM
EXTRA_OECONF += " --disable-mcs-build "
# Instead, get the mcs tree from a different build (see mono-mcs-intermediate)

do_install_prepend() {
	install -d ${D}
	pushd ${D}
	tar -xzf ${STAGING_DATADIR_NATIVE}/mono-mcs/mono-mcs-${PV}.tar.gz
	popd
}

do_install_append() {
	# mono-mcs-intermediate builds and installs jay (a Yacc for Java and C#),
	# however, jay is not being cross-compiled and thus only
	# available for the buildhost architecture, so remove it
	# entirely
	pushd ${D}
	rm -rf ./usr/share/man/man1/jay.1 ./usr/share/jay \
	    ./usr/share/jay/README.jay \
	    ./usr/bin/jay
	popd

	# Not packaged with the default rules and apparently
	# not used for anything
	rm -rf ${D}${datadir}/mono-1.0/mono/cil/cil-opcodes.xml
}

inherit mono

# Import file definitions from Debian
require mono_2.x-files.inc

# Add some packages
PACKAGES_append = " mono-doc mono mono-runtime"

FILES_mono-doc_append = " /usr/share/libgc-mono/ "

FILES_mono = ""
ALLOW_EMPTY_mono = "1"
RDEPENDS_mono = "mono-common mono-jit"

FILES_mono-runtime = ""
ALLOW_EMPTY_mono-runtime = "1"
RDEPENDS_mono-runtime = "mono-jit mono-gac"

RDEPENDS_mono-jit = "mono-common"

FILES_libmono-dev =+ " /usr/lib/libmono.la /usr/lib/libmono-profiler-cov.la /usr/lib/libmono-profiler-aot.la \
	/usr/lib/libMonoPosixHelper.la /usr/lib/libMonoSupportW.la"
FILES_libmono-dbg =+ " /usr/lib/.debug/libmono*.so.* /usr/lib/.debug/libikvm-native.so \
	/usr/lib/.debug/libMonoPosixHelper.so /usr/lib/.debug/libMonoSupportW.so"

# Packages not included in Debian
PACKAGES_prepend = "libnunit2.2-cil-dbg libnunit2.2-cil-dev libnunit2.2-cil \
	libmono-cecil0.5-cil-dbg libmono-cecil0.5-cil-dev libmono-cecil0.5-cil \
	libmono-db2-1.0-cil-dbg libmono-db2-1.0-cil-dev libmono-db2-1.0-cil"

FILES_libnunit2.2-cil = "/usr/lib/mono/gac/nunit.*/2.2.* /usr/lib/mono/1.0/nunit.*.dll"
FILES_libnunit2.2-cil-dev = "/usr/lib/pkgconfig/mono-nunit.pc"
FILES_libnunit2.2-cil-dbg = "/usr/lib/mono/gac/nunit*/2.2.*/nunit.*.dll.mdb"

FILES_libmono-cecil0.5-cil = "/usr/lib/mono/gac/Mono.Cecil/0.5.*"
FILES_libmono-cecil0.5-cil-dbg = "/usr/lib/mono/gac/Mono.Cecil/0.5.0.1__0738eb9f132ed756/Mono.Cecil.dll.mdb"

FILES_libmono-db2-1.0-cil = "/usr/lib/mono/gac/IBM.Data.DB2/1.0* /usr/lib/mono/1.0/IBM.Data.DB2.dll"
FILES_libmono-db2-1.0-cil-dbg = "/usr/lib/mono/gac/IBM.Data.DB2/1.0*/IBM.Data.DB2.dll.mdb"

# Move .pc files
FILES_libmono-cairo1.0-cil-dev = "/usr/lib/pkgconfig/mono-cairo.pc"
PACKAGES =+ " libmono-cairo1.0-cil-dev "

SRC_URI[md5sum] = "6dfc8364f6e761d558f134a707bae421"
SRC_URI[sha256sum] = "44fc0eddf56c0abe861190051fceec6e223122c5835967459c69fbf98ee52067"
