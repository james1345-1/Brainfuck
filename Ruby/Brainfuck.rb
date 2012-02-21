class Brainfuck
	
	def putc(x); $stdout.putc(x); end
	def getc(); $stdin.getc(); end
	
	def initialize(string)
		@mem = Array.new(30000){|i| 0}
		@mp = 0
		
		@string = string
		@ip = 0
		@EOF = @string.length
	end
	
	def run()
		
		while(@ip < @EOF)
			c = @string[@ip]
			case c
			when '>'
				@mp+=1
			when '<'
				@mp-=1
			when '+'
				@mem[@mp]+=1
			when '-'
				@mem[@mp]-=1
			when '.'
				putc @mem[@mp]
			when ','
				@mem[@mp] = getc
			when '['
				if(@mem[@mp] == 0)
					while (@string[@ip]!=']')
						@ip+=1
					end
				end
			when ']'
				if(@mem[@mp] != 0)
					while (@string[@ip]!='[')
						@ip+=1
					end
				end
			end
			@ip+=1
		end
	end
end

$b = Brainfuck.new("++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.")
$b.run