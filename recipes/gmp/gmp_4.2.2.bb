PR = "r1"

SRC_URI_append += "file://02_noexec-stack.diff \
		   file://03_gnu89-inline.diff \
		   file://mpf_set_str_c.diff \
		   file://sh4-asmfix.patch \
		  "
require gmp.inc

SRC_URI[gmp.md5sum] = "7ce52531644e6d12f16911b7e3151f3f"
SRC_URI[gmp.sha256sum] = "2b2c0aacafa2dc41f4604b381349d49596921e38a58bf782b0d70b33f548657b"
