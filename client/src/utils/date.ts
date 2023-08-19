export function dateFormat(date: Date) {
  const month: number = date.getMonth() + 1
  const day: number = date.getDate()

  const formattedMonth: string = month >= 10 ? `${month}` : `0${month}`
  const formattedDay: string = day >= 10 ? `${day}` : `0${day}`

  return `${date.getFullYear()}-${formattedMonth}-${formattedDay}`
}
